package com.treat.customer.data.datasource.remote

import com.treat.customer.base.BaseApiProvider
import com.treat.customer.data.datasource.interfaces.IBranchRemoteDS
import com.treat.customer.data.datasource.remote.api.ApiService
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchDetailsResponse
import kotlinx.coroutines.flow.Flow

class BranchRemoteDSImpl(private val apiService: ApiService) : IBranchRemoteDS, BaseApiProvider() {
    override suspend fun getBranchDetailsUseCase(branchId: String): Flow<NetworkResource<BranchDetailsResponse>> {
        return apiRequest { apiService.getBranchDetails(branchId) }
    }
}