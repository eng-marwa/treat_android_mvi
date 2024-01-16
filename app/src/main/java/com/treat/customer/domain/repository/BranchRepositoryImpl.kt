package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.interfaces.IBranchRemoteDS
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchDetailsResponse
import kotlinx.coroutines.flow.Flow

class BranchRepositoryImpl(private val iBranchRemoteDS: IBranchRemoteDS) : IBranchRepository {
    override suspend fun getBranchDetailsUseCase(branchId: String): Flow<NetworkResource<BranchDetailsResponse>> {
        return iBranchRemoteDS.getBranchDetailsUseCase(branchId)
    }
}