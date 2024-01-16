package com.treat.customer.presentation.main.ui.branches

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.AddFavoriteResponse
import com.treat.customer.data.model.BranchDetailsData
import com.treat.customer.data.model.BranchDetailsResponse
import com.treat.customer.data.model.Images
import com.treat.customer.data.model.ServiceCategoriesData
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.Services
import com.treat.customer.databinding.FragmentBranchDetailsBinding
import com.treat.customer.presentation.main.ui.favorites.FavoritesViewModel
import com.treat.customer.presentation.main.ui.home.HomeViewModel
import com.treat.customer.presentation.main.ui.home.ServiceAdapter
import com.treat.customer.utils.extensions.showSnack
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BranchDetailsFragment : Fragment(), CategoryAdapter.OnItemClick, ServiceAdapter.OnItemClick {

    private lateinit var branchDetails: BranchDetailsData
    private var isFav: Boolean = false
    private var branchId: String? = null
    private val viewModel: BranchViewModel by viewModel<BranchViewModel>()
    private val homeViewModel: HomeViewModel by viewModel<HomeViewModel>()
    private val favoritesViewModel: FavoritesViewModel by viewModel<FavoritesViewModel>()
    private val branchSliderAdapter by lazy {
        context?.let { BranchSliderAdapter(it) }
    }
    private val categoryAdapter by lazy {
        CategoryAdapter(this)
    }
    private val serviceAdapter by lazy {
        ServiceAdapter(this)
    }
    private val aboutBottomSheet by lazy {
        AboutBranchBottomSheet()
    }
    private var _binding: FragmentBranchDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBranchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            branchId = it.getString("branchId")
        }
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.branchDetailsStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onBranchDetailsSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onBranchDetailsFailed(it.throwable)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            favoritesViewModel.addToFavorites.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onAddFavoriteBranchSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onAddFavoriteBranchFailed(it.throwable)
                    else -> {}
                }
            }
        }
        lifecycleScope.launch {
            homeViewModel.serviceCategoriesStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onAllCategoriesSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onAllCategoriesBranchFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onAllCategoriesBranchFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            "${throwable?.getMessage()}",
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onAllCategoriesSuccess(data: ServiceCategoriesResponse?) {
        data?.let {
            categoryAdapter.setList(it.data)
        }
    }

    private fun onAddFavoriteBranchFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            "${throwable?.getMessage()}",
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onAddFavoriteBranchSuccess(data: AddFavoriteResponse?) {
        data?.let {
            showSnack(
                binding.root,
                data.message,
                null,
                isError = false,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )
        }
    }

    private fun onBranchDetailsSuccess(data: BranchDetailsResponse?) {
        data?.data?.let {
            branchDetails = it
            setupSlider(it.images)
            isFav = it.isFavourite
            if (it.isFavourite) {
                binding.btnFavoriteBranch.setImageResource(R.drawable.fav1)

            } else {
                binding.btnFavoriteBranch.setImageResource(R.drawable.fav)
            }
            binding.branchHeaderLayout.lbBranchName.text = it.name
            try {
                Glide.with(this).asBitmap().load(it.logo).centerCrop()
                    .into(binding.branchHeaderLayout.ivBranch)
            } catch (e: Exception) {
                Log.e("TAG", "onBranchDetailsSuccess: ${e.toString()}")
            }
            binding.lbRateBranch.text = it.rate
            binding.branchHeaderLayout.lbAddress.text = it.address ?: ""
            if (it.isSpecial) {
                binding.lbBanner.visibility = View.VISIBLE
                context?.let {
                    binding.lbBanner.text = requireContext().getString(R.string.special)
                    binding.lbBanner.setTextColor(requireContext().getColor(R.color.white))
                }

            } else if (it.coupons != null) {
                binding.lbBanner.text =
                    "${it.coupons?.title} ${it.coupons?.value} ${if (it.coupons?.id == 1) "%" else ""}"
                binding.lbBanner.visibility = View.VISIBLE
                context?.let {
                    binding.lbBanner.setTextColor(requireContext().getColor(R.color.white))
                }

            } else {
                binding.lbBanner.visibility = View.GONE
            }
            if (it.serviceType != null) {
                homeViewModel.getServiceCategories(it.serviceType!!)
            }
            if (it.services.isNotEmpty()) {
                serviceAdapter.setServices(it.services)
            }
        }
    }

    private fun onBranchDetailsFailed(throwable: BaseException?) {
        showSnack(
            binding.root, throwable?.getMessage(), null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun initViews() {
        if (branchId != null) {
            viewModel.getBranchDetails(branchId!!)
        }
        setupAppBar()
        initList()
        binding.btnFavoriteBranch.setOnClickListener {
            if (branchDetails.isFavourite) {
                binding.btnFavoriteBranch.setImageResource(R.drawable.fav)
                favoritesViewModel.addToFavorites(branchId!!)
                branchDetails.isFavourite = false

            } else {
                binding.btnFavoriteBranch.setImageResource(R.drawable.fav1)
                favoritesViewModel.addToFavorites(branchId!!)
                branchDetails.isFavourite = true
            }
        }
        binding.btnBook.setOnClickListener {
            context?.let {
                binding.btnBook.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray4B))
                binding.btnSendGift.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grayEB))
                binding.btnBook.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                )
                binding.btnSendGift.iconTint =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray4A))
                binding.btnSendGift.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray77))
                )
                binding.btnBookNow.icon = null

            }
            binding.btnBookNow.setText(R.string.book)

        }
        binding.btnSendGift.setOnClickListener {
            context?.let {
                binding.btnBook.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grayEB))
                binding.btnSendGift.backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray4B))
                binding.btnBookNow.icon =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.bx_gift)
                binding.btnSendGift.iconTint =
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                binding.btnBook.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray77))
                )
                binding.btnSendGift.setTextColor(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))
                )

            }
            binding.btnBookNow.setText(R.string.send)

        }
        binding.branchHeaderLayout.lbAbout.setOnClickListener {
            if (!aboutBottomSheet.isAdded) {
                aboutBottomSheet.setAboutData(
                    branchDetails
                )
                aboutBottomSheet.show(childFragmentManager, "about")
            }
        }
    }


    private fun initList() {
        binding.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

        binding.rvServices.layoutManager =             LinearLayoutManager(requireContext())
        binding.rvServices.adapter=serviceAdapter

    }

    private fun setupSlider(data: ArrayList<Images>) {
        context?.let {
            val sliderView: SliderView = binding.imageSlider
            if (data.isNotEmpty()) {
                branchSliderAdapter!!.setData(data)
            }
            sliderView.setSliderAdapter(branchSliderAdapter!!)
            sliderView.setIndicatorAnimation(IndicatorAnimationType.COLOR)
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            sliderView.startAutoCycle()

        }
    }

    private fun setupAppBar() {
        binding.ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BranchDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun itemSelected(item: ServiceCategoriesData) {
    }

    override fun itemSelected(item: Services) {
    }
}