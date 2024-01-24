package com.treat.customer.presentation.location_branches

import LocationHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.treat.customer.BuildConfig
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.Branches
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.databinding.FragmentBranchesLocationBinding
import com.treat.customer.presentation.ITemActivity
import com.treat.customer.presentation.location.LocationViewModel
import com.treat.customer.presentation.main.ui.branches.BranchAdapter
import com.treat.customer.presentation.main.ui.favorites.FavoritesViewModel
import com.treat.customer.presentation.main.ui.home.HomeViewModel
import com.treat.customer.utils.BitmapVector
import com.treat.customer.utils.extensions.getCurrentPosition
import com.treat.customer.utils.extensions.showSnack
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BranchesLocationFragment : Fragment(), BranchAdapter.OnItemClick,
    LocationHelper.LocationResultListener {
    private var branches: ArrayList<Branches>? = null
    private var placeMarkers: Marker? = null
    private lateinit var placesClient: PlacesClient
    private val branchAdapter by lazy {
        BranchAdapter(this)
    }

    private var googleMap: GoogleMap? = null
    private val viewModel: LocationViewModel by viewModel<LocationViewModel>()
    private val homeViewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()

    private var _binding: FragmentBranchesLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBranchesLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        Places.initialize(requireContext(), BuildConfig.MAP_API_KAY)
        mapFragment.getMapAsync { googleMap ->
            this.googleMap = googleMap
            initViews()
            observeViewModel()
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            //binding.loader.show()
            homeViewModel.branches.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onViewBranchesSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onViewBranchesFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onViewBranchesFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onViewBranchesSuccess(data: BranchesResponse?) {
        data?.let {
            if (it.data != null) {
                this.branches = it.data?.branches
                branchAdapter.setBranches(it.data!!.branches)
                it.data?.let {
                    it.branches.forEach { branches ->
                        if (branches.addressLat != null && branches.addressLong != null) {
                            googleMap?.addMarker(MarkerOptions().apply {
                                position(
                                    LatLng(
                                        branches.addressLat!!.toDouble(),
                                        branches.addressLong!!.toDouble()
                                    )
                                )
                                icon(
                                    BitmapVector.bitmapDescriptorFromVector(
                                        requireActivity(), (R.drawable.branch_pin)
                                    )
                                )
                                title(branches.name)
                            })?.tag = branches.id
                            googleMap?.setOnMarkerClickListener {
                                if (it.tag == branches.id) {
                                    val index = data.data?.branches?.indexOf(branches) ?: 0
                                    binding.rvBranches.smoothScrollToPosition(index)
                                }
                                true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        context?.let {
            placesClient = Places.createClient(requireContext())
            useAutoCompletePlaces()
        }

        initList()
        val savedLocation = viewModel.getSavedLocation()
        val list = savedLocation?.split("#")
        if (list != null && list.size == 2) {
            val latLng = LatLng(list[0].toDouble(), list[1].toDouble())
            setMapMarker(latLng)
            homeViewModel.getBranches(
                lat = "${latLng.latitude}",
                lng = "${latLng.longitude}",
                date = null,
                gender = null,
                serviceCategoryIds = null,
                serviceType = null
            )
        }
        binding.btnCurrent.setOnClickListener {
            context?.let {
                val locationHelper = LocationHelper(it);
                locationHelper.setLocationResultListener(this)
                // Check if GPS is enabled
                if (locationHelper.isGPSEnabled) {
                    locationHelper.currentLocation()
                } else {
                    // If GPS is not enabled, prompt the user to open location settings
                    locationHelper.openLocationSettings();
                }
            }
        }


    }

    private fun initList() {
        binding.rvBranches.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvBranches.adapter = branchAdapter
        binding.rvBranches.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = binding.rvBranches.getCurrentPosition()
                  //  onPageChanged(position)
                }
            }
        })
    }

    private fun onPageChanged(position: Int) {

        if (branches != null) {
            val branch = branches!![position]
            if (branch.addressLat != null && branch.addressLong != null) {
                if (googleMap != null) {
                    googleMap?.addMarker(MarkerOptions().apply {
                        this.position(
                            LatLng(
                                branch.addressLat!!.toDouble(),
                                branch.addressLong!!.toDouble()
                            )
                        )
                        this.icon(
                            BitmapVector.bitmapDescriptorFromVector(
                                requireActivity(), (R.drawable.from_search_pin)
                            )
                        )
                    })
                }
            }
        }
    }

    private fun setMapMarker(latLng: LatLng) {
        if (googleMap != null) {
            googleMap?.addMarker(MarkerOptions().apply {
                this.position(latLng)
                this.icon(
                    BitmapVector.bitmapDescriptorFromVector(
                        requireActivity(), (R.drawable.current_loc_pin)
                    )
                )
            })
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9f))
        }
    }

    private fun useAutoCompletePlaces() {
        val autocompleteFragment = AutocompleteSupportFragment.newInstance()
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG
            )
        )

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Handle the selected place
                val placeName = place.name
                val placeId = place.id
                // Do something with the selected place
                if (googleMap != null) {
                    if (placeMarkers != null && placeMarkers?.tag != placeId) {
                        placeMarkers?.remove()
                    }
                    placeMarkers = googleMap?.addMarker(MarkerOptions().apply {
                        if (place.latLng != null) {
                            this.position(place.latLng!!)
                            this.icon(
                                BitmapVector.bitmapDescriptorFromVector(
                                    requireActivity(), (R.drawable.from_search_pin)
                                )
                            )
                            homeViewModel.getBranches(
                                lat = "${place.latLng?.latitude}",
                                lng = "${place.latLng?.longitude}",
                                date = null,
                                gender = null,
                                serviceCategoryIds = null,
                                serviceType = null
                            )
                        }
                    })
                    placeMarkers?.tag = place.id
                }
            }

            override fun onError(status: Status) {
                // Handle errors
            }
        })

        childFragmentManager.beginTransaction()
            .replace(R.id.autocomplete_container, autocompleteFragment).commit()

    }


    companion object {
        @JvmStatic
        fun newInstance() = BranchesLocationFragment().apply {
            arguments = Bundle().apply {

            }
        }


    }

    override fun itemSelected(item: Branches) {
        startActivity(
            Intent(requireContext(), ITemActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }.putExtra("ITEM", R.string.branch_details).putExtra("BRANCH", item.id)
        )
    }

    override fun addItemToFavorite(item: Branches) {
        showSnack(
            binding.root,
            "${item.name}${getString(R.string.is_added)}",
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        favoritesViewModel.addToFavorites(item.id!!)
    }

    override fun removeItemFromFavorite(item: Branches) {
        showSnack(
            binding.root,
            "${item.name}${getString(R.string.is_removed)}",
            null,
            isError = false,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
        favoritesViewModel.addToFavorites(item.id!!)
    }

    override fun onLocationResult(latitude: Double, longitude: Double) {
        Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
        viewModel.saveLocation(latitude, longitude)
        viewModel.updateLocation(latitude, longitude)
        setMapMarker(LatLng(latitude, longitude))
    }
}

