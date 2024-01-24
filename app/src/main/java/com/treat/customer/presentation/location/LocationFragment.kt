package com.treat.customer.presentation.location

import LocationHelper
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.treat.customer.R
import com.treat.customer.databinding.FragmentLocationBinding
import com.treat.customer.databinding.FragmentProfileBinding
import com.treat.customer.presentation.auth.profile.ProfileViewModel
import com.treat.customer.utils.BitmapVector.bitmapDescriptorFromVector
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationFragment : Fragment(), LocationHelper.LocationResultListener {
    private var googleMap: GoogleMap? = null

    companion object {
        fun newInstance() = LocationFragment()
    }

    private val viewModel: LocationViewModel by viewModel<LocationViewModel>()
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            this.googleMap = googleMap
            initViews()
            // Customize and interact with the map here
        }

    }

    private fun initViews() {
        val savedLocation = viewModel.getSavedLocation()
        val list = savedLocation?.split("#")
        if (list != null && list.size == 2) {
            val latLng = LatLng(list[0].toDouble(), list[1].toDouble())
            setMapMarker(latLng)
        }
        context?.let {
            val locationHelper = LocationHelper(it);
            // Check if GPS is enabled
            if (locationHelper.isGPSEnabled) {
                locationHelper.setLocationResultListener(this)
                locationHelper.currentLocation()
            } else {
                // If GPS is not enabled, prompt the user to open location settings
                locationHelper.openLocationSettings();
            }
        }
    }

    private fun setMapMarker(latLng: LatLng) {
        if (googleMap != null) {
            googleMap?.addMarker(MarkerOptions().apply {
                this.position(latLng)
                this.icon(
                    bitmapDescriptorFromVector(
                        requireActivity(),
                        (R.drawable.current_loc_pin)
                    )
                )
            })
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 9f))
        }
    }

    override fun onLocationResult(latitude: Double, longitude: Double) {
        Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
        viewModel.saveLocation(latitude, longitude)
        viewModel.updateLocation(latitude, longitude)
        setMapMarker(LatLng(latitude, longitude))
    }


}