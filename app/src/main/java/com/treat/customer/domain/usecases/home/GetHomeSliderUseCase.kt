package com.treat.customer.domain.usecases.home

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.HomeSliderResponse
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class GetHomeSliderUseCase (private val iHomeRepository: IHomeRepository) {
    suspend operator fun invoke(): Flow<NetworkResource<HomeSliderResponse>> =
        iHomeRepository.getHomeSlider()
}