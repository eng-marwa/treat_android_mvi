package com.treat.customer.presentation.filter

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.databinding.FragmentCategoryBottomSheetBinding
import com.treat.customer.domain.entities.MultipleChoiceData
import com.treat.customer.presentation.main.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryBottomSheet(private val onSelectItems: OnSelectItems) : BottomSheetDialogFragment() ,
    FilterCategoryAdapter.MultiOptionClick {
    private val selectedItems = mutableSetOf<ServiceCategoriesData>()
    private var _binding: FragmentCategoryBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter by lazy {
        FilterCategoryAdapter(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.setPeekHeight(resources.displayMetrics.heightPixels)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }
    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {

    }

    private fun initViews() {
        initList()
        binding.btnAdd.setOnClickListener {
            if(selectedItems.isNotEmpty()){
                onSelectItems.onItemsSelected(selectedItems)
                viewModel.selectedItems = selectedItems.toMutableList()
                dismiss()
            }
        }
        if(viewModel.selectedItems.isNotEmpty()){
            categoryAdapter.setSelectedItems(viewModel.selectedItems)
        }
    }

    private fun initList() {
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = categoryAdapter
    }
    fun setCategoriesList(categories: List<ServiceCategoriesData>) {
        categoryAdapter.setList(categories)
    }
    interface OnSelectItems {
        fun onItemsSelected(categories: MutableSet<ServiceCategoriesData>)
    }
    override fun onMultiOptionSelect(option: MultipleChoiceData<ServiceCategoriesData>) {
        selectedItems.add(option.data!!)
        viewModel.selectedItems.add(option.data)
    }
    override fun onMultiOptionUnselect(option: MultipleChoiceData<ServiceCategoriesData>) {
        selectedItems.remove(option.data!!)
        viewModel.selectedItems.remove(option.data)

    }
}