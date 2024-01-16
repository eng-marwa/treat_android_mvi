package com.treat.customer.data.datasource.interfaces

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.TransferPointsResponse
import kotlinx.coroutines.flow.Flow

interface IUserMoreRemoteDS {
    suspend fun getFQA(): Flow<NetworkResource<FQAResponse>>
    suspend fun getMyWallet(): Flow<NetworkResource<MyWalletResponse>>
    suspend fun getMyPoints(): Flow<NetworkResource<MyPointsResponse>>
    suspend fun transferToWallet(pointId:String): Flow<NetworkResource<TransferPointsResponse>>
    suspend fun getAppSettings(): Flow<NetworkResource<SettingsResponse>>

}