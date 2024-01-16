package com.treat.customer.domain.usecases.home

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class GetGenderByServiceTypeUseCase(private val iHomeRepository: IHomeRepository)  {
    suspend operator fun invoke(
        serviceType: String,
    ): Flow<NetworkResource<GenderResponse>> =
        iHomeRepository.getGenderByServiceType(serviceType)
}