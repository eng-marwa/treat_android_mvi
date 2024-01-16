package com.treat.customer.domain.usecases.more

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.domain.repository.IUserMoreRepository
import kotlinx.coroutines.flow.Flow

class GetMyPointsUseCase(private val iUserMoreRepository: IUserMoreRepository) {
    suspend operator fun invoke(
    ): Flow<NetworkResource<MyPointsResponse>> =
        iUserMoreRepository.getMyPoints()
}