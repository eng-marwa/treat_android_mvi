package com.treat.customer.presentation.main.ui.more.my_wallet

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.domain.usecases.more.GetMyWalletUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyWalletViewModel(private val getMyWalletUseCase: GetMyWalletUseCase) : BaseViewModel() {
    val myWalletStatus =
        MutableStateFlow<ViewState<MyWalletResponse>>(ViewState.Empty())

    fun getMyWallet() {
        viewModelScope.launch {
            getMyWalletUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                myWalletStatus.value = state
            }
        }
    }
}