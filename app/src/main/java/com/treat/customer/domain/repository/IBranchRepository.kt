package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchDetailsResponse
import kotlinx.coroutines.flow.Flow

interface IBranchRepository {
    suspend fun getBranchDetailsUseCase(branchId: String): Flow<NetworkResource<BranchDetailsResponse>>


}