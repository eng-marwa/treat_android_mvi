package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.repository.IUserAuthRepository
import kotlinx.coroutines.flow.Flow

class VerifyAccountUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    suspend operator fun invoke(phone: String, otp: String): Flow<NetworkResource<ProfileResponse>> =
        iUserAuthRepository.verifyAccount(phone,otp)
}