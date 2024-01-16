package com.treat.customer.domain.usecases.home

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.SpecificServicesResponse
import com.treat.customer.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class GetSpecificServicesUseCase(private val iHomeRepository: IHomeRepository) {
    suspend operator fun invoke(
        categoryId: String,
        gender: String
    ): Flow<NetworkResource<SpecificServicesResponse>> =
        iHomeRepository.getSpecificServices(categoryId, gender)
}