package com.treat.customer.data.datasource.interfaces

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchDetailsResponse
import kotlinx.coroutines.flow.Flow

interface IBranchRemoteDS {
    suspend fun getBranchDetailsUseCase(branchId: String): Flow<NetworkResource<BranchDetailsResponse>>
}