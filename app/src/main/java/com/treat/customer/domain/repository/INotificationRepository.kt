package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

interface INotificationRepository {
    suspend fun getNotifications(): Flow<NetworkResource<NotificationResponse>>
    suspend fun markOneNotificationRead(notificationId: String): Flow<NetworkResource<TreatResponse>>
    suspend fun markAllNotificationsRead(): Flow<NetworkResource<TreatResponse>>
}