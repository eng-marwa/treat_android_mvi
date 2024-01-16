package com.treat.customer.presentation.main.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.Branches
import com.treat.customer.data.model.BranchesData
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.HomeSliderData
import com.treat.customer.data.model.HomeSliderResponse
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.ServiceTypeData
import com.treat.customer.data.model.ServiceTypesResponse
import com.treat.customer.data.model.Services
import com.treat.customer.databinding.FragmentHomeBinding
import com.treat.customer.presentation.ITemActivity
import com.treat.customer.presentation.auth.profile.ProfileViewModel
import com.treat.customer.presentation.filter.FilterBottomSheet
import com.treat.customer.presentation.main.ui.branches.BranchAdapter
import com.treat.customer.presentation.main.ui.favorites.FavoritesViewModel
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), ServiceTypeAdapter.OnItemClick, FilterBottomSheet.SearchFilter,
    BranchAdapter.OnItemClick {
    private val categories: ArrayList<ServiceCategoriesData> = arrayListOf()
    private var tabTitles: ArrayList<String> = arrayListOf()
    private var genderTab: ArrayList<GenderData> = arrayListOf()
//    private val tabAdapter by lazy {
//        HomeTabAdapter(this)
//    }

    private val branchAdapter by lazy {
        BranchAdapter(this)
    }
    private val searchBottomSheet by lazy {
        FilterBottomSheet()
    }

    private val serviceTypeAdapter by lazy {
        ServiceTypeAdapter(this)
    }
    private val sliderAdapter by lazy {
        context?.let { SliderAdapter(it) }
    }
    private val viewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val profileViewModel: ProfileViewModel by viewModel<ProfileViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()

    }

    private fun observeViewModel() {
        binding.loader.show()
        lifecycleScope.launch {
            viewModel.homeSliderStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onSliderSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onSliderFailed(it.throwable)
                    else -> {
                        // binding.loader.show()
                    }
                }
            }
        }
//        lifecycleScope.launch {
//            viewModel.serviceTypeStatus.collect {
//                when (it) {
//                    is BaseViewModel.ViewState.Loaded -> onServiceTypesSuccess(it.data)
//                    is BaseViewModel.ViewState.Failed -> onServiceTypesFailed(it.throwable)
//                    else -> {
//                        binding.loader.show()
//                    }
//                }
//            }
//        }
        lifecycleScope.launch {
            binding.loader.show()
            viewModel.serviceCategoriesStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onServiceCategoriesSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onServiceCategoriesFailed(it.throwable)
                    else -> {

                    }
                }
            }
        }
        lifecycleScope.launch {
            binding.loader.show()
            viewModel.branches.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onViewBranchesSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onViewBranchesFailed(it.throwable)
                    else -> {}
                }
            }
        }
//        lifecycleScope.launch {
//            viewModel.genderByService.collect {
//                when (it) {
//                    is BaseViewModel.ViewState.Loaded -> onGenderByServiceSuccess(it.data)
//                    is BaseViewModel.ViewState.Failed -> onGenderByServiceFailed(it.throwable)
//                    else -> {
//                        binding.loader.show()
//                    }
//                }
//            }
//        }
    }

//    private fun onGenderByServiceFailed(throwable: BaseException?) {
//        binding.loader.hide()
//        showToast(throwable?.getMessage())
//    }

//    private fun onGenderByServiceSuccess(data: GenderResponse?) {
//        binding.loader.hide()
//
//        data?.let {
//            if (it.data.isEmpty()) {
//                binding.subTab.visibility = View.GONE
//            } else {
//                binding.subTab.visibility = View.VISIBLE
//            }
//            genderTab.clear()
//            it.data.forEach { genderTab.add(it) }
//            setupSubTabs()
//        }
//    }

    private fun onServiceCategoriesFailed(throwable: BaseException?) {
        binding.loader.hide()
        showToast(throwable?.getMessage())
    }

    private fun onServiceCategoriesSuccess(data: ServiceCategoriesResponse?) {
        binding.loader.hide()
        tabTitles.clear()
        categories.clear()
        categories.add(0, ServiceCategoriesData(name = getString(R.string.all)))
        data?.data?.map {
            tabTitles.add(it.name!!)
            categories.add(it)
        }
        setupTabs()
        if (viewModel.serviceType!!.id == "1") {
            showToast(viewModel.genderId)
            viewModel.getBranches(
                if (categories[0].id == null) null else arrayListOf(categories[0].id!!),
                viewModel.serviceType?.id!!,
                viewModel.genderId,
                lat = null,
                lng = null
            )
        } else {
            viewModel.getBranches(
                if (categories[0].id == null) null else arrayListOf(categories[0].id!!),
                viewModel.serviceType?.id!!,
                null,
                lat = null,
                lng = null
            )
        }
    }


//    private fun onServiceTypesFailed(throwable: BaseException?) {
//        binding.loader.hide()
//
//        showToast(throwable?.getMessage())
//    }

    //    private fun onServiceTypesSuccess(data: ServiceTypesResponse?) {
//        binding.loader.hide()
//
//        val serviceTypes = mutableListOf<ServiceTypeData>()
//        data?.let {
//            it.data.forEach { e ->
//                if (e.id == "1" || e.id == "2") {
//                    serviceTypes.add(e)
//                }
//            }
//            serviceTypeAdapter.setServiceTypes(serviceTypes)
//            if (serviceType?.id != "2") {
//                setupSubTabs()
//                viewModel.callGenderAndServiceCategorySync(it.data[0])
//
//            } else {
//                binding.subTab.visibility = View.GONE
//                viewModel.getServiceCategories(it.data[0].id!!)
//
//            }
////            viewModel.getGenderByService(it.data[0])
////            viewModel.getServiceCategories(it.data[0])
//        }
//    }
    private fun onViewBranchesFailed(throwable: BaseException?) {
        showToast(throwable?.getMessage())
    }

    private fun onViewBranchesSuccess(data: BranchesResponse?) {
        data?.let {
            if (it.data != null) {
                branchAdapter.setBranches(it.data!!.branches)
            }
        }

    }

    private fun onSliderFailed(throwable: BaseException?) {
        binding.loader.hide()

        showToast(throwable?.getMessage())
    }

    private fun onSliderSuccess(data: HomeSliderResponse?) {
        binding.loader.hide()

        data?.let {
            setupSlider(it.data)
        }

    }

    private fun initViews() {
        viewModel.getHomeSlider()
        viewModel.getServiceCategories("2")
        val serviceTypes = arrayListOf<ServiceTypeData>(
            ServiceTypeData("2", getString(R.string.location), null),
            ServiceTypeData("1", getString(R.string.home), null),

            )
        viewModel.serviceType = serviceTypes[0]
        serviceTypeAdapter.setServiceTypes(serviceTypes)
        if (viewModel.serviceType?.id == "2") {
            binding.subTab.visibility = View.GONE
        } else {
            binding.subTab.visibility = View.VISIBLE
            setupSubTabs()
        }
        binding.searchLayout.ivSearchFilter.setOnClickListener {
            if (searchBottomSheet.isAdded.not()) {
                searchBottomSheet.show(childFragmentManager, "filter")
            }
        }
        binding.searchLayout.ivLocation.setOnClickListener {

        }
        binding.toolBar.frNotification.setOnClickListener {
            startActivity(
                Intent(requireContext(), ITemActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.putExtra("ITEM", R.string.title_notifications)
            )
        }
        initList()
    }

    private fun initList() {
        binding.rvServiceType.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvServiceType.adapter = serviceTypeAdapter

        binding.rvBranches.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvBranches.adapter = branchAdapter

    }

    private fun setupSlider(data: ArrayList<HomeSliderData>) {
        context?.let {
            val sliderView: SliderView = binding.imageSlider
            sliderAdapter!!.setData(data)
            sliderView.setSliderAdapter(sliderAdapter!!)
            sliderView.setIndicatorAnimation(IndicatorAnimationType.COLOR)
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            sliderView.startAutoCycle()

        }
    }

    private fun setupTabs() {
        if (binding.tab.tabCount != 0) {
            binding.tab.removeAllTabs()
        }
        categories.forEach {
            binding.tab.addTab(binding.tab.newTab().setText(it.name))
        }
        binding.tab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (viewModel.serviceType!!.id == "1") {
                    viewModel.getBranches(
                        if (categories[tab?.position!!].id == null) null else arrayListOf(categories[tab.position].id!!),
                        viewModel.serviceType?.id!!,
                        if(binding.subTab.selectedTabPosition == 0) null else "${binding.subTab.selectedTabPosition + 1}",
                        lat = null,
                        lng = null
                    )
                } else {
                    viewModel.getBranches(
                        if (categories[tab?.position!!].id == null) null else arrayListOf(categories[tab.position].id!!),
                        viewModel.serviceType?.id!!,
                        null,
                        lat = null,
                        lng = null
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupSubTabs() {
//        if (binding.subTab.tabCount != 0) {
//            binding.subTab.removeAllTabs()
//        }
        genderTab.add(GenderData(name =  getString(R.string.all)))
        genderTab.add(GenderData("1", getString(R.string.male)))
        genderTab.add(GenderData("2", getString(R.string.female)))

        genderTab.forEach {
            binding.subTab.addTab(binding.subTab.newTab().setText(it.name))
        }
        binding.subTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(subTab: TabLayout.Tab?) {
                if (subTab!!.position==0){
                    viewModel.genderId = null
                }else {
                    viewModel.genderId = genderTab[subTab.position].id!!
                }
                if (viewModel.serviceType!!.id == "1") {
                    viewModel.getBranches(
                        if (categories[binding.tab.selectedTabPosition].id == null) null else arrayListOf(categories[binding.tab.selectedTabPosition].id!!),
                        viewModel.serviceType?.id!!,
                        if(viewModel.genderId==null) null else genderTab[subTab.position].id!!,
                        lat = null,
                        lng = null
                    )
                } else {
                    viewModel.getBranches(
                        if (categories[binding.tab.selectedTabPosition].id == null) null else arrayListOf(categories[binding.tab.selectedTabPosition].id!!),
                        viewModel.serviceType?.id!!,
                        null,
                        lat = null,
                        lng = null
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemSelected(item: ServiceTypeData) {
        if (item.id == "2") {
            binding.subTab.visibility = View.GONE
            viewModel.genderId = null
//            viewModel.getServiceCategories(item.id!!)
        } else {
            binding.subTab.visibility = View.VISIBLE
            genderTab.clear()
            setupSubTabs()
//            viewModel.callGenderAndServiceCategorySync(item)
        }
        viewModel.getServiceCategories(item.id!!)
        viewModel.serviceType = item

    }

    override fun onFilterApply(service: Services) {

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

}



