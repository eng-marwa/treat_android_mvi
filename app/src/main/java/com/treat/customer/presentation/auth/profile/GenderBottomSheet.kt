package com.treat.customer.presentation.auth.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treat.customer.data.model.GenderData
import com.treat.customer.databinding.FragmentGenderBottomSheetBinding

class GenderBottomSheet(private var onItemSelect: GenderSelect) : BottomSheetDialogFragment(),
    GenderAdapter.OnItemClick {

    private lateinit var _binding: FragmentGenderBottomSheetBinding
    private val binding get() = _binding

    private val genderAdapter by lazy {
        GenderAdapter(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenderBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        onEvent()
    }

    private fun initList() {
        binding.rvGender.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGender.adapter = genderAdapter
    }


    private fun onEvent() {
        _binding.root.setOnClickListener {
            if (genderAdapter.isGenderSelected()) {
                onItemSelect.onGenderSelect(genderAdapter.getSelectedOption()!!)
            }
            dismiss()
        }
    }

    override fun itemSelected(item: GenderData) {
        onItemSelect.onGenderSelect(item)
        dismiss()
    }

    fun setGenderList(genderList: MutableList<GenderData>) {
        genderAdapter.setGenderList(genderList)
    }


    interface GenderSelect {
        fun onGenderSelect(gender: GenderData)
    }
}