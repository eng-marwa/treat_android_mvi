package com.treat.customer.presentation.auth.otp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.treat.customer.R
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.databinding.FragmentOTBinding
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.main.HomeActivity
import com.treat.customer.utils.extensions.hideKeyboard
import com.treat.customer.utils.extensions.showToast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.math.log

class OTPFragment : Fragment() {

    companion object {
        fun newInstance() = OTPFragment()
    }

    private var phone: String? = null
    private var otpCode: String? = null
    private var resendAllowed: Boolean = false
    private var counter: CountDownTimer? = null
    private var timer: CountDownTimer? = null

    private val viewModel: OTViewModel by viewModel<OTViewModel>()
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()
    private var _binding: FragmentOTBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOTBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phone = arguments?.getString("phone")
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.resendOTP.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onResendCodeSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onResendCodeFailed(it.throwable)
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.verifyAccount.collect {
                when (it) {
                    is BaseViewModel.ViewState.Loaded -> onVerifyAccountSuccess(it.data)
                    is BaseViewModel.ViewState.Failed -> onVerifyAccountFailed(it.throwable)
                    else -> {}
                }
            }
        }
    }

    private fun onResendCodeFailed(throwable: BaseException?) {
        showToast(throwable?.getMessage())
    }

    private fun onResendCodeSuccess(data: TreatResponse?) {
        showToast(getString(R.string.check_phone_number))

    }

    private fun onVerifyAccountSuccess(data: ProfileResponse?) {
        Log.d("TAG1", "onVerifyAccountSuccess:$data ")
        loginViewModel.saveUserData(data)
        loginViewModel.setLoggedIn(true)
        if (data?.data?.completedProfile == true) {
            startActivity(
                Intent(requireContext(), HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
            activity?.finish()

        } else {
            findNavController().navigate(R.id.action_OTPFragment_to_profileFragment)
        }
    }

    private fun onVerifyAccountFailed(throwable: BaseException?) {
        showToast(throwable?.getMessage())
    }

    private fun initViews() {
        binding.btnConfirm.setOnClickListener {
            timer?.cancel()
            counter?.cancel()
            if (otpCode != null && phone != null) {
                viewModel.verifyAccount(phone!!, otpCode!!)
            }
        }
        binding.btnBack.setOnClickListener {
            timer?.cancel()
            counter?.cancel()
            findNavController().navigate(R.id.action_OTPFragment_to_loginFragment)
        }
        binding.btnConfirm.visibility = View.INVISIBLE
        binding.btnResend.visibility = View.INVISIBLE
        startTimer()
        watchEditTexts()

        binding.btnResend.setOnClickListener {
            resendCode()
        }
        hideKeyboard()
    }


    private fun watchEditTexts() {
        binding.etNum1.doOnTextChanged { text, start, before, count ->
            if (text?.length == 1) {
                binding.etNum1.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.auth_green
                    )
                )
            }
        }
        binding.etNum2.doOnTextChanged { text, start, before, count ->
            if (text?.length == 1) {
                binding.etNum2.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.auth_green
                    )
                )
            }
        }
        binding.etNum3.doOnTextChanged { text, start, before, count ->
            if (text?.length == 1) {
                binding.etNum3.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.auth_green
                    )
                )
            }
        }
        binding.etNum4.doOnTextChanged { text, start, before, count ->
            if (text?.length == 1) {
                binding.etNum4.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.auth_green
                    )
                )
            }
        }


        binding.etNum1.doAfterTextChanged {
            if (binding.etNum1.text?.length == 1) {
                binding.etNum2.requestFocus()
                binding.etNum1.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
        }
        binding.etNum2.doAfterTextChanged {
            if (binding.etNum2.text?.length == 1) {
                binding.etNum3.requestFocus()
                binding.etNum2.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
        }
        binding.etNum3.doAfterTextChanged {
            if (binding.etNum3.text?.length == 1) {
                binding.etNum4.requestFocus()
                binding.etNum3.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
        }
        binding.etNum4.doAfterTextChanged {
            if (binding.etNum4.text?.length == 1) {
                binding.etNum4.requestFocus()
                binding.etNum4.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.btnConfirm.visibility = View.VISIBLE
                timer?.cancel()
                counter?.cancel()
                otpCode =
                    "${binding.etNum1.text}${binding.etNum2.text}${binding.etNum3.text}${binding.etNum4.text}"
                hideKeyboard()

            }
        }
    }


    private fun startTimer() {
        binding.lbTimer.visibility = View.VISIBLE
        binding.btnResend.visibility = View.GONE
        counter = object : CountDownTimer(TimeUnit.SECONDS.toMillis(30), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.lbTimer.text = "${TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)}"
            }

            override fun onFinish() {
                binding.btnResend.visibility = View.VISIBLE
                binding.lbExpire.visibility = View.INVISIBLE
                binding.lbTimer.visibility = View.INVISIBLE
                resendAllowed = true
            }
        }.start()
    }

    private fun startCounter() {
        binding.lbTimer.visibility = View.VISIBLE

        binding.btnResend.visibility = View.GONE
        counter = object : CountDownTimer(TimeUnit.SECONDS.toMillis(30), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.lbTimer.text = "${TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)}"
            }

            override fun onFinish() {
                binding.btnResend.visibility = View.VISIBLE
                binding.lbTimer.visibility = View.INVISIBLE
                resendAllowed = true
            }
        }.start()
    }

    private fun resendCode() {
        if (resendAllowed && phone != null) {
            viewModel.sendOtpCode(phone!!)
        } else {
            // startCounter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        counter?.cancel()
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        counter?.cancel()
        timer?.cancel()
    }

}