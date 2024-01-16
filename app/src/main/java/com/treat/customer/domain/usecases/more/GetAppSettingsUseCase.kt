package com.treat.customer.domain.usecases.more

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.domain.repository.IUserMoreRepository
import kotlinx.coroutines.flow.Flow

class GetAppSettingsUseCase(private val iUserMoreRepository: IUserMoreRepository) {
    suspend operator fun invoke(): Flow<NetworkResource<SettingsResponse>> =
        iUserMoreRepository.getAppSettings()
}