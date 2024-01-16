package com.treat.customer.domain.usecases.branches

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchDetailsResponse
import com.treat.customer.domain.repository.IBranchRepository
import kotlinx.coroutines.flow.Flow

class GetBranchDetailsUseCase (private val iBranchRepository: IBranchRepository) {
    suspend operator fun invoke(branchId:String): Flow<NetworkResource<BranchDetailsResponse>> =
        iBranchRepository.getBranchDetailsUseCase(branchId)
}