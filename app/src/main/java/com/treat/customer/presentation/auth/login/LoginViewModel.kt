package com.treat.customer.presentation.auth.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.usecases.auth.GetSavedUserDataUseCase
import com.treat.customer.domain.usecases.auth.SaveUserDataUseCase
import com.treat.customer.domain.usecases.auth.SetLoginStatusUseCase
import com.treat.customer.domain.usecases.auth.UserLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: UserLoginUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val setLoginStatusUseCase: SetLoginStatusUseCase,
    private val getUserDataUseCase:GetSavedUserDataUseCase
) : BaseViewModel() {
    val loginStatus = MutableStateFlow<ViewState<LoginResponse>>(ViewState.Empty())
    fun doLogin(phoneNumber: String) {
        if (validatePhone(phoneNumber)) {
            viewModelScope.launch {
                loginUseCase(phoneNumber).collect {
                    val state = when (it.status) {
                        is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                        is NetworkStatus.Failure -> ViewState.Failed(it.error)
                        else -> ViewState.Empty()
                    }
                    loginStatus.value = state
                }
            }

        } else {
            loginStatus.value = ViewState.Failed(BaseException(message = "INVALID_PHONE_ERROR"))
        }
    }

    fun validatePhone(phoneNumber: String): Boolean {
        return Patterns.PHONE.matcher(phoneNumber).matches()
    }

    fun setLoggedIn(b: Boolean) {
        setLoginStatusUseCase.setLoginStatus(b)

    }

    fun saveUserData(data: ProfileResponse?) {
        saveUserDataUseCase.saveUserData(data)

    }

    fun getUserData(): ProfileResponse? {
        return  getUserDataUseCase.getSavedUserData()
    }

}