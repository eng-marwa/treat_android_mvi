package com.treat.customer.presentation.filter

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.ServiceTypeData
import com.treat.customer.data.model.Services
import com.treat.customer.databinding.FragmentSearchBottomSheetBinding
import com.treat.customer.domain.entities.Day
import com.treat.customer.presentation.auth.profile.ProfileViewModel
import com.treat.customer.presentation.main.search.SearchViewModel
import com.treat.customer.presentation.main.ui.home.HomeViewModel
import com.treat.customer.presentation.main.ui.home.ServiceTypeAdapter
import com.treat.customer.utils.SingleLineCalender
import com.treat.customer.utils.SingleLineCalender.getCurrentDayPosition
import com.treat.customer.utils.SingleLineCalender.getCurrentMonth
import com.treat.customer.utils.SingleLineCalender.nextMonth
import com.treat.customer.utils.SingleLineCalender.previousMonth
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class FilterBottomSheet() :
    BottomSheetDialogFragment(), FilterGenderAdapter.OnItemClick,
    CategoryBottomSheet.OnSelectItems, CalendarAdapter.OnDateSelected,
    ServiceTypeAdapter.OnItemClick, Filterable {

    private var selectedItems: List<ServiceCategoriesData> = arrayListOf()
    private val genderList: ArrayList<GenderData> = arrayListOf()
    private val categories: ArrayList<ServiceCategoriesData> = arrayListOf()


    private var _binding: FragmentSearchBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModel<ProfileViewModel>()
    private val homeViewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()

    private val filterGenderAdapter by lazy {
        FilterGenderAdapter(this)
    }
    private val filterCategoryAdapter by lazy {
        FilterSelectedCategoryAdapter()
    }
    private val categoryBottomSheet by lazy {
        CategoryBottomSheet(this)
    }
    private val calendarAdapter by lazy {
        CalendarAdapter(this)
    }
    private val serviceTypeAdapter by lazy {
        ServiceTypeAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            behavior.setPeekHeight(resources.displayMetrics.heightPixels)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val bottomSheet =
                dialog?.findViewById<View>(R.id.container)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        setupSingleLineCalender()
        initList()
        val serviceTypes = arrayListOf<ServiceTypeData>(
            ServiceTypeData("1", getString(R.string.home), null),
            ServiceTypeData("2", getString(R.string.location), null)
        )
        viewModel.serviceType = serviceTypes[0]
        serviceTypeAdapter.setServiceTypes(serviceTypes)
        homeViewModel.callGenderAndServiceCategorySync(viewModel.serviceType!!)

        binding.btnMenu.setOnClickListener {
            if (categories.isNotEmpty())
                if (categoryBottomSheet.isAdded.not()) {
                    categoryBottomSheet.setCategoriesList(categories)
                    categoryBottomSheet.show(childFragmentManager, "category")
                }
        }
        binding.lbServiceCategory.setOnClickListener {
            binding.btnMenu.callOnClick()
        }

        binding.btnShowResult.setOnClickListener {
            var itemsIds: ArrayList<String>? = null
            if (selectedItems.isNotEmpty()) {
                itemsIds = arrayListOf()
                selectedItems.forEach { itemsIds.add(it.id!!) }
            }
            homeViewModel.getBranches(
                itemsIds,
                viewModel.serviceType!!.id,
                if(viewModel.gender == null) null else viewModel.gender!!.id,
                lat = null,
                lng = null
            )
        dismiss()
    }

    binding.cancel.setOnClickListener{
        dismiss()
    }
    binding.reset.setOnClickListener{

    }
}

private fun initList() {
    binding.rvServiceType.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    binding.rvServiceType.adapter = serviceTypeAdapter
    binding.rvSelectedCategories.layoutManager = FlexboxLayoutManager(requireContext())
    binding.rvSelectedCategories.adapter = filterCategoryAdapter

    binding.rvGender.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    binding.rvGender.adapter = filterGenderAdapter
    binding.rvSingleLineCalender.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    binding.rvSingleLineCalender.adapter = calendarAdapter
    if (getCurrentDayPosition(binding.lbMonthYear.text.toString()) > 0) {
        binding.rvSingleLineCalender.layoutManager?.scrollToPosition(
            getCurrentDayPosition(
                binding.lbMonthYear.text.toString()
            ) - 1
        )
    }
}
private fun setupSingleLineCalender() {
    binding.lbMonthYear.text = getCurrentMonth()
    if (binding.lbMonthYear.text.toString() != getCurrentMonth()) {
        binding.ivArrowLeft.isEnabled = false
        binding.ivArrowLeft.isClickable = false
        context?.let {
            binding.ivArrowLeft.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.grayA4
                ), android.graphics.PorterDuff.Mode.SRC_IN
            );
        }
    } else {
        binding.ivArrowLeft.isEnabled = true
        binding.ivArrowLeft.isClickable = true
        context?.let {
            binding.ivArrowLeft.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.orangeB2
                ), android.graphics.PorterDuff.Mode.SRC_IN
            );
        }
    }
    val forAllYears =
        SingleLineCalender.generateDaysForCurrentMonth(binding.lbMonthYear.text.toString())
    calendarAdapter.setCalenderData(forAllYears, binding.lbMonthYear.text.toString())

    binding.ivArrowLeft.setOnClickListener {
        if (binding.lbMonthYear.text.toString() != getCurrentMonth()) {
            binding.lbMonthYear.text = previousMonth(binding.lbMonthYear.text.toString())
            val forAllYears =
                SingleLineCalender.generateDaysForCurrentMonth(binding.lbMonthYear.text.toString())
            calendarAdapter.setCalenderData(forAllYears, binding.lbMonthYear.text.toString())
        }
    }
    binding.ivArrowRight.setOnClickListener {
        binding.lbMonthYear.text = nextMonth(binding.lbMonthYear.text.toString())
        val forAllYears =
            SingleLineCalender.generateDaysForCurrentMonth(binding.lbMonthYear.text.toString())
        calendarAdapter.setCalenderData(forAllYears, binding.lbMonthYear.text.toString())
    }


}

private fun observeViewModel() {
    lifecycleScope.launch {
        homeViewModel.serviceCategoriesStatus.collect {
            when (it) {
                is BaseViewModel.ViewState.Loaded -> onServiceCategoriesSuccess(it.data)
                is BaseViewModel.ViewState.Failed -> onServiceCategoriesFailed(it.throwable)
                else -> {}
            }
        }
    }
    lifecycleScope.launch {
        homeViewModel.genderByService.collect {
            when (it) {
                is BaseViewModel.ViewState.Loaded -> onGenderByServiceSuccess(it.data)
                is BaseViewModel.ViewState.Failed -> onGenderByServiceFailed(it.throwable)
                else -> {}
            }
        }
    }
}

private fun onGenderByServiceFailed(throwable: BaseException?) {
    showToast(throwable?.getMessage())
}

private fun onGenderByServiceSuccess(data: GenderResponse?) {
    data?.let {
        genderList.clear()
        genderList.addAll(it.data)
    }
    filterGenderAdapter.setGenderList(genderList)
}

private fun onServiceCategoriesFailed(throwable: BaseException?) {
    showToast(throwable?.getMessage())
}

private fun onServiceCategoriesSuccess(data: ServiceCategoriesResponse?) {
    data?.let {
        categories.clear()
        categories.addAll(it.data)
        categoryBottomSheet.setCategoriesList(it.data)

    }
}


private fun onServiceTypesFailed(throwable: BaseException?) {
    showToast(throwable?.getMessage())
}


interface SearchFilter {
    fun onFilterApply(service: Services)
}

override fun itemSelected(item: GenderData) {
    viewModel.gender = item
}

override fun onDaySelected(day: Day, monthYear: String) {

}

override fun itemSelected(item: ServiceTypeData) {
    Log.d("TAG", "itemSelected: ")
    homeViewModel.callGenderAndServiceCategorySync(item)
    viewModel.serviceType = item
}

override fun getFilter(): Filter {
    return searchedFilter
}

private val searchedFilter: Filter = object : Filter() {
    override fun performFiltering(constraint: CharSequence): FilterResults {


        val results = FilterResults()
        return results
    }

    override fun publishResults(constraint: CharSequence, results: FilterResults) {

    }

}

override fun onItemsSelected(categories: List<ServiceCategoriesData>) {
    selectedItems = categories
    filterCategoryAdapter.setSelectedList(categories)
}
}