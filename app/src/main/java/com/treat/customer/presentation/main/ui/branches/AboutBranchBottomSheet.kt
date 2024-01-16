package com.treat.customer.presentation.main.ui.branches

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treat.customer.data.model.BranchDetailsData
import com.treat.customer.data.model.OpeningHours
import com.treat.customer.databinding.FragmentAboutBranchBottomSheetBinding
import com.treat.customer.utils.extensions.showToast

class AboutBranchBottomSheet :
    BottomSheetDialogFragment() {

    private var openingHours: ArrayList<OpeningHours> = arrayListOf()
    private var about: String? = null
    private var email: String? = null
    private var addressLat: String? = null
    private var addressLong: String? = null
    private var phone: String? = null
    private var startWork: String? = null
    private var endWork: String? = null
    private var _binding: FragmentAboutBranchBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val openingHoursAdapter by lazy {
        OpeningHoursAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBranchBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
//        binding.lbAboutText.text = about
//        binding.lbEmailText.text = email
//        binding.lbLocationText.text = "${addressLat} - ${addressLong}"
//        binding.lbPhoneText.text = phone
//        binding.lbHours.text = openingHours.joinToString { "${it.day}\n${it.start}-${it.end}\n\n" }
        initList()
    }

    private fun initList() {
        binding.rvOpeningHours.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvOpeningHours.adapter = openingHoursAdapter


    }

    fun setAboutData(
        branchDetailsData: BranchDetailsData,
    ) {
        openingHoursAdapter.setList(branchDetailsData.openingHours)
    }
}