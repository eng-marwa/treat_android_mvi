package com.treat.customer.data.datasource.remote

import com.treat.customer.base.BaseApiProvider
import com.treat.customer.data.datasource.interfaces.IUserMoreRemoteDS
import com.treat.customer.data.datasource.remote.api.ApiService
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.TransferPointsResponse
import kotlinx.coroutines.flow.Flow

class UserMoreRemoteDSImpl(private val apiService: ApiService) : IUserMoreRemoteDS,
    BaseApiProvider() {
    override suspend fun getFQA(): Flow<NetworkResource<FQAResponse>> {
        return apiRequest { apiService.getFQA() }

    }

    override suspend fun getMyWallet(): Flow<NetworkResource<MyWalletResponse>> {
        return apiRequest { apiService.myWallet() }
    }
    override suspend fun getMyPoints(): Flow<NetworkResource<MyPointsResponse>> {
        return apiRequest { apiService.myPoints() }
    }

    override suspend fun transferToWallet(pointId:String): Flow<NetworkResource<TransferPointsResponse>> {
        return apiRequest { apiService.pointTransfer(pointId) }
    }

    override suspend fun getAppSettings(): Flow<NetworkResource<SettingsResponse>> {
        return apiRequest { apiService.getAppSettings() }
    }
}