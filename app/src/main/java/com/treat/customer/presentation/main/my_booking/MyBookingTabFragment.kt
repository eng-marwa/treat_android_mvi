package com.treat.customer.presentation.main.my_booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.treat.customer.databinding.FragmentMybookingTabBinding

class MyBookingTabFragment : Fragment() {
    private var _binding: FragmentMybookingTabBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MyBookingTabFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMybookingTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}