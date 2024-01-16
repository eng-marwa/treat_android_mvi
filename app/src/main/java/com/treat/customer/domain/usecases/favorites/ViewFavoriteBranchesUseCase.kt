package com.treat.customer.domain.usecases.favorites

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.AddFavoriteResponse
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class ViewFavoriteBranchesUseCase (private val iHomeRepository: IHomeRepository) {
    suspend operator fun invoke(): Flow<NetworkResource<BranchesResponse>> =
        iHomeRepository.viewFavoriteBranches()
}