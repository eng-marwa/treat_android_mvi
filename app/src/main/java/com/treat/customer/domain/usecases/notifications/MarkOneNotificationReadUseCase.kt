package com.treat.customer.domain.usecases.notifications

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.repository.INotificationRepository
import kotlinx.coroutines.flow.Flow

class MarkOneNotificationReadUseCase (private val iNotificationRepository: INotificationRepository) {
    suspend operator fun invoke(notificationId: String): Flow<NetworkResource<TreatResponse>> =
        iNotificationRepository.markOneNotificationRead(notificationId)
}