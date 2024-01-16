package com.treat.customer.presentation.main.ui.more.fqa

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.usecases.more.GetFQAUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FQAViewModel(private val getFQAUseCase: GetFQAUseCase) : BaseViewModel() {
    val fqaStatus =
        MutableStateFlow<ViewState<FQAResponse>>(ViewState.Empty())

    fun getFQA() {
        viewModelScope.launch {
            getFQAUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                fqaStatus.value = state
            }
        }
    }
}