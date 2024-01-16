package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.interfaces.IUserMoreRemoteDS
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.TransferPointsResponse
import kotlinx.coroutines.flow.Flow

class UserMoreRepositoryImpl(private val iUserMoreRemoteDS: IUserMoreRemoteDS,) :IUserMoreRepository{
    override suspend fun getFQA(): Flow<NetworkResource<FQAResponse>> {
        return iUserMoreRemoteDS.getFQA()
    }

    override suspend fun getMyWallet(): Flow<NetworkResource<MyWalletResponse>> {
        return iUserMoreRemoteDS.getMyWallet()
    }

    override suspend fun getMyPoints(): Flow<NetworkResource<MyPointsResponse>> {
        return iUserMoreRemoteDS.getMyPoints()
    }

    override suspend fun transferToWallet(pointId:String): Flow<NetworkResource<TransferPointsResponse>> {
        return iUserMoreRemoteDS.transferToWallet(pointId)
    }

    override suspend fun getAppSettings(): Flow<NetworkResource<SettingsResponse>> {
        return iUserMoreRemoteDS.getAppSettings()
    }
}