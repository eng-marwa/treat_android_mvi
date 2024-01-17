package com.treat.customer.presentation.location_branches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.treat.customer.R
import com.treat.customer.databinding.FragmentBranchesLocationBinding
import com.treat.customer.databinding.FragmentLocationBinding
import com.treat.customer.presentation.location.LocationFragment
import com.treat.customer.presentation.location.LocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BranchesLocationFragment : Fragment() {


    private val viewModel: LocationViewModel by viewModel<LocationViewModel>()
    private var _binding: FragmentBranchesLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBranchesLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            // Customize and interact with the map here
        }

    }

    private fun observeViewModel() {

    }

    private fun initViews() {
        viewModel.getSavedLocation()
        binding.btnCurrent.setOnClickListener {
            viewModel.getCurrentLocation()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BranchesLocationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}