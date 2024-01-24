package com.treat.customer.presentation.main.ui.branches

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.BranchDetailsResponse
import com.treat.customer.domain.usecases.branches.GetBranchDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BranchViewModel(
    private val getBranchDetailsUseCase: GetBranchDetailsUseCase,
) :
    BaseViewModel() {
    val branchDetailsStatus = MutableStateFlow<ViewState<BranchDetailsResponse>>(ViewState.Empty())
    fun getBranchDetails(branchId: String) {
        viewModelScope.launch {
            getBranchDetailsUseCase(branchId).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> ViewState.Failed(it.error)
                    else -> ViewState.Empty()
                }
                branchDetailsStatus.value = state
            }
        }
    }


}