package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.domain.repository.IUserAuthRepository
import kotlinx.coroutines.flow.Flow

class GetGenderUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    suspend operator fun invoke(): Flow<NetworkResource<GenderResponse>> =
        iUserAuthRepository.getGender()
}