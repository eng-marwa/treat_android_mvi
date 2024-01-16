package com.treat.customer.presentation.main.ui.favorites

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.AddFavoriteResponse
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.domain.usecases.favorites.AddBranchToFavoriteUseCase
import com.treat.customer.domain.usecases.favorites.ViewFavoriteBranchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val addBranchToFavoriteUseCase: AddBranchToFavoriteUseCase,
    private val viewFavoriteBranchesUseCase: ViewFavoriteBranchesUseCase

) : BaseViewModel() {
    val favoriteBranch =
        MutableStateFlow<ViewState<BranchesResponse>>(ViewState.Empty())
    val addToFavorites =
        MutableStateFlow<ViewState<AddFavoriteResponse>>(ViewState.Empty())

    fun getFavoriteBranches() {
        viewModelScope.launch {
            viewFavoriteBranchesUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                favoriteBranch.value = state
            }
        }
    }

    fun addToFavorites(branchId: String) {
        viewModelScope.launch {
            addBranchToFavoriteUseCase(branchId).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                addToFavorites.value = state
            }
        }
    }

}