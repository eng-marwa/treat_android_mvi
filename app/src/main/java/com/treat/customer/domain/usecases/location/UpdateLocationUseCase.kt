package com.treat.customer.domain.usecases.location

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.repository.IUserAuthRepository
import kotlinx.coroutines.flow.Flow

class UpdateLocationUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Flow<NetworkResource<ProfileResponse>> =
        iUserAuthRepository.updateLocation(latitude,longitude)
}