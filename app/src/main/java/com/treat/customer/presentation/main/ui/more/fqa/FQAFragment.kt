package com.treat.customer.presentation.main.ui.more.fqa

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
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.databinding.FragmentFQABinding
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FQAFragment : Fragment() {

    private var _binding: FragmentFQABinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FQAFragment()
    }

    private val fqaAdapter by lazy {
        FQAAdapter()
    }
    private val viewModel: FQAViewModel by viewModel<FQAViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFQABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.fqaStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onFQASuccess(it.data!!)
                    is BaseViewModel.ViewState.Failed -> onFQAFailed(it.throwable)
                    else -> {}
                }
            }
        }

    }

    private fun onFQAFailed(throwable: BaseException?) {
      showToast(getString(R.string.something_wrong))
    }

    private fun onFQASuccess(data: FQAResponse) {
        fqaAdapter.setFAQData(data.data)
    }

    private fun initViews() {
        viewModel.getFQA()
        binding.toolBar.frNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_fqa_to_navigation_notification)
        }
        setupAppBar()
        initList()
    }

    private fun setupAppBar() {
        binding.titleToolBar.lbTitle.setText(R.string.fqa)
        binding.titleToolBar.ivBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initList() {
        binding.rvFQA.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFQA.adapter = fqaAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}