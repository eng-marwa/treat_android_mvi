package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.interfaces.INotificationRemoteDS
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(private val iNotificationRemoteDS: INotificationRemoteDS) : INotificationRepository{
    override suspend fun getNotifications(): Flow<NetworkResource<NotificationResponse>> {
        return  iNotificationRemoteDS.getNotifications()
    }

    override suspend fun markOneNotificationRead(notificationId: String): Flow<NetworkResource<TreatResponse>> {
        return iNotificationRemoteDS.markOneNotificationRead(notificationId)
    }

    override suspend fun markAllNotificationsRead(): Flow<NetworkResource<TreatResponse>> {
        return iNotificationRemoteDS.markAllNotificationsRead()
    }
}