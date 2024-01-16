package com.treat.customer.presentation.main.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.SettingsData
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.usecases.auth.DisableAccountUseCase
import com.treat.customer.domain.usecases.auth.LogoutRemoteUseCase
import com.treat.customer.domain.usecases.auth.LogoutUseCase
import com.treat.customer.domain.usecases.more.GetAppSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoreViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
    private val logoutRemoteUseCase: LogoutRemoteUseCase,
    private val disableAccountUseCase: DisableAccountUseCase
) : BaseViewModel() {
    val logoutStatus = MutableStateFlow<ViewState<TreatResponse>>(ViewState.Empty())
    val disableAccountStatus = MutableStateFlow<ViewState<TreatResponse>>(ViewState.Empty())
    val appSettingsStatus = MutableStateFlow<ViewState<SettingsResponse>>(ViewState.Empty())

    fun logout() {
        logoutUseCase.logout()
        viewModelScope.launch {
            logoutRemoteUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                logoutStatus.value = state
            }
        }
    }

    fun disableAccount() {
        viewModelScope.launch {
            disableAccountUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                disableAccountStatus.value = state
            }
        }
    }
    fun getAppSettings() {
        viewModelScope.launch {
            getAppSettingsUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                appSettingsStatus.value = state
            }
        }
    }
}


