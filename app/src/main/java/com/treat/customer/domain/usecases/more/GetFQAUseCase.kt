package com.treat.customer.domain.usecases.more

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.repository.IUserAuthRepository
import com.treat.customer.domain.repository.IUserMoreRepository
import kotlinx.coroutines.flow.Flow

class GetFQAUseCase(private val iUserMoreRepository: IUserMoreRepository) {
    suspend operator fun invoke(): Flow<NetworkResource<FQAResponse>> =
        iUserMoreRepository.getFQA()
}