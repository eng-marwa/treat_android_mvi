package com.treat.customer.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.databinding.FragmentLoginBinding
import com.treat.customer.utils.extensions.hideKeyboard
import com.treat.customer.utils.extensions.showSnack
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModel<LoginViewModel>()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginStatus.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onLoginSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onLoginFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onLoginFailed(throwable: BaseException?) {
        if(throwable?.getMessage() == "INVALID_PHONE_ERROR"){
            showSnack(
                binding.root,
                getString(R.string.invalid_phone_error_message),
                null,
                isError = true,
                showButton = false,
                buttonTitle = null,
                onClick = null
            )
        }else {
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
    }

    private fun onLoginSuccess(data: LoginResponse?) {
        navigateToOtp()
    }

    private fun navigateToOtp() {
        findNavController().navigate(
            R.id.action_loginFragment_to_OTPFragment,
            bundleOf("phone" to binding.etPhoneNumber.text.toString())
        )
    }

    private fun initViews() {
        watchEditText()
        binding.btnLogin.setOnClickListener {
            if (!binding.ckAgreement.isChecked) {
                showSnack(
                    binding.root,
                    getString(R.string.please_accept_agreement),
                    null,
                    isError = true,
                    showButton = false,
                    buttonTitle = null,
                    onClick = null
                )
            } else {
                viewModel.doLogin(binding.etPhoneNumber.text.toString())
            }
        }
        hideKeyboard()

    }

    private fun watchEditText() {
        binding.etPhoneNumber.doAfterTextChanged {
            viewModel.validatePhone(
                binding.etPhoneNumber.text.toString(),
            )
        }
    }
}