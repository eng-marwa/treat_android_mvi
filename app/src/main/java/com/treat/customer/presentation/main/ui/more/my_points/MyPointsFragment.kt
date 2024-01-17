package com.treat.customer.presentation.main.ui.more.my_points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.TransferPointsResponse
import com.treat.customer.databinding.FragmentMyPointsBinding
import com.treat.customer.utils.DateTimePickerManager
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPointsFragment : Fragment() {

    companion object {
        fun newInstance() = MyPointsFragment()
    }

    private val myPointsAdapter by lazy {
        MyPointsAdapter()
    }

    private val viewModel: MyPointsViewModel by viewModel<MyPointsViewModel>()
    private var _binding: FragmentMyPointsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        viewModel.getMyPoints()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_points_to_navigation_notification)
        }
        setupAppBar()
        initList()
        binding.btnAddToWallet.setOnClickListener {
            viewModel.transferPointsToWallet("1")
        }
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.my_points)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.myPointsStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onMyPointsSuccess(it.data!!)
                    is BaseViewModel.ViewState.Failed -> onMyPointsFailed(it.throwable)
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.myPointsTransferStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onPointTransferSuccess(it.data!!)
                    is BaseViewModel.ViewState.Failed -> onPointTransferFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onPointTransferFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onPointTransferSuccess(data: TransferPointsResponse) {
        showSnack(
            binding.root,
            getString(R.string.points_transfer_successfully),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onMyPointsFailed(throwable: BaseException?) {
        showSnack(
            binding.root,
            throwable?.getMessage(),
            null,
            isError = true,
            showButton = false,
            buttonTitle = null,
            onClick = null
        )
    }

    private fun onMyPointsSuccess(data: MyPointsResponse) {
        binding.lbResultCount.text = "${data.data?.details?.size} Results"
        binding.lbPointsNumber.text = "${data.data?.unpaid} ${getString(R.string.points)}"
        binding.lbLastUpdate.text = "${getString(R.string.last_update)} ${
            DateTimePickerManager().getFormattedDate(
                "dd MMMM yyyy",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
                data.data?.lastUpdate
            )
        }"
        myPointsAdapter.setMyPointsList(data.data?.details!!)

    }

    private fun initList() {
        binding.rvPoints.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPoints.adapter = myPointsAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}