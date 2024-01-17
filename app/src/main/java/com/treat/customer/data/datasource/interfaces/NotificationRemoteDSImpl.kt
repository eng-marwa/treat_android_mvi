package com.treat.customer.data.datasource.interfaces

import com.treat.customer.base.BaseApiProvider
import com.treat.customer.data.datasource.remote.api.ApiService
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.SpecificServicesResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

class NotificationRemoteDSImpl(private val apiService: ApiService) : INotificationRemoteDS,
    BaseApiProvider() {
    override suspend fun getNotifications(): Flow<NetworkResource<NotificationResponse>> {
        return apiRequest { apiService.getNotifications() }
    }

    override suspend fun markOneNotificationRead(notificationId: String): Flow<NetworkResource<TreatResponse>> {
        return apiRequest { apiService.readNotification(notificationId) }

    }

    override suspend fun markAllNotificationsRead(): Flow<NetworkResource<TreatResponse>> {
        return apiRequest { apiService.readAllNotifications() }

    }
}