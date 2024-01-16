package com.treat.customer.presentation.location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import com.treat.customer.R
import com.treat.customer.databinding.FragmentLocationBinding
import com.treat.customer.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationFragment : Fragment() {

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
            // Customize and interact with the map here
        }

    }

}