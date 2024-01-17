package com.treat.customer.presentation.main.notification

import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.usecases.notifications.GetNotificationUseCase
import com.treat.customer.domain.usecases.notifications.MarkAllNotificationsReadUseCase
import com.treat.customer.domain.usecases.notifications.MarkOneNotificationReadUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val markAllNotificationsReadUseCase: MarkAllNotificationsReadUseCase,
    private val markOneNotificationReadUseCase: MarkOneNotificationReadUseCase
) :
    BaseViewModel() {
    val notifications =
        MutableStateFlow<ViewState<NotificationResponse>>(ViewState.Empty())
    val allNotificationsRead =
        MutableStateFlow<ViewState<TreatResponse>>(ViewState.Empty())
    val oneNotificationRead =
        MutableStateFlow<ViewState<TreatResponse>>(ViewState.Empty())

    fun getNotifications() {
        viewModelScope.launch {
            getNotificationUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                notifications.value = state
            }
        }
    }

    fun makeOneNotificationRead(notificationId: String) {
        viewModelScope.launch {
            markOneNotificationReadUseCase.invoke(notificationId).collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                oneNotificationRead.value = state
            }
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            markAllNotificationsReadUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                allNotificationsRead.value = state
            }
        }
    }
}