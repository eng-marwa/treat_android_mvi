package com.treat.customer.data.datasource.interfaces

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

interface INotificationRemoteDS {
    suspend fun getNotifications(): Flow<NetworkResource<NotificationResponse>>
    suspend fun markOneNotificationRead(notificationId: String): Flow<NetworkResource<TreatResponse>>
    suspend fun markAllNotificationsRead(): Flow<NetworkResource<TreatResponse>>

}