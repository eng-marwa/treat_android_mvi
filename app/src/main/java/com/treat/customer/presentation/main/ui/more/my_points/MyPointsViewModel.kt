package com.treat.customer.presentation.main.ui.more.my_points

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.TransferPointsResponse
import com.treat.customer.domain.usecases.more.GetMyPointsUseCase
import com.treat.customer.domain.usecases.more.TransferPointsToWalletUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyPointsViewModel(private val getMyPointsUseCase: GetMyPointsUseCase,private val transferPointsToWalletUseCase: TransferPointsToWalletUseCase) : BaseViewModel() {
    val myPointsStatus =
        MutableStateFlow<ViewState<MyPointsResponse>>(ViewState.Empty())
  val myPointsTransferStatus =
        MutableStateFlow<ViewState<TransferPointsResponse>>(ViewState.Empty())

    fun getMyPoints() {
        viewModelScope.launch {
            getMyPointsUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                myPointsStatus.value = state
            }
        }
    }

    fun transferPointsToWallet(id: String) {
        viewModelScope.launch {
            transferPointsToWalletUseCase(id).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                myPointsTransferStatus.value = state
            }
        }
    }
}