package com.treat.customer.presentation.main.ui.more.my_wallet

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
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.databinding.FragmentMyWalletBinding
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyWalletFragment : Fragment() {

    companion object {
        fun newInstance() = MyWalletFragment()
    }

    private val myWalletAdapter by lazy {
        MyWalletAdapter()
    }
    private val viewModel: MyWalletViewModel by viewModel<MyWalletViewModel>()

    private var _binding: FragmentMyWalletBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        viewModel.getMyWallet()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_my_wallet_to_navigation_notification)
        }
        setupAppBar()
        initList()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.myWalletStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onMyWalletSuccess(it.data!!)
                    is BaseViewModel.ViewState.Failed -> onMyWalletFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onMyWalletFailed(throwable: BaseException?) {
        showToast(throwable?.getMessage())
    }

    private fun onMyWalletSuccess(data: MyWalletResponse) {
        myWalletAdapter.setWalletDataList(data.data.details)
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.my_wallet)
        binding.titleToolBar.ivBack.visibility = View.VISIBLE
        binding.titleToolBar.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initList() {
        binding.rvMyWallet.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyWallet.adapter = myWalletAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}