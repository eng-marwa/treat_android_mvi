package com.treat.customer.domain.usecases.more

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.TransferPointsResponse
import com.treat.customer.domain.repository.IUserMoreRepository
import kotlinx.coroutines.flow.Flow

class TransferPointsToWalletUseCase(private val iUserMoreRepository: IUserMoreRepository) {
    suspend operator fun invoke(pointId:String
    ): Flow<NetworkResource<TransferPointsResponse>> =
        iUserMoreRepository.transferToWallet(pointId)
}