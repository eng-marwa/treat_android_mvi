package com.treat.customer.presentation.auth.otp

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.usecases.auth.ResendOTPUseCase
import com.treat.customer.domain.usecases.auth.VerifyAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OTViewModel(private val resendOTPUseCase: ResendOTPUseCase, private val  verifyAccountUseCase: VerifyAccountUseCase) : BaseViewModel() {
    val resendOTP = MutableStateFlow<ViewState<TreatResponse>>(ViewState.Empty())
    val verifyAccount = MutableStateFlow<ViewState<ProfileResponse>>(ViewState.Empty())

    fun sendOtpCode(phone: String) {
        viewModelScope.launch {
            resendOTPUseCase(phone).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                resendOTP.value = state
            }
        }
    }

    fun verifyAccount(phone:String, otp:String) {
        viewModelScope.launch {
            verifyAccountUseCase.invoke(phone, otp).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                verifyAccount.value = state
            }
        }
    }

}