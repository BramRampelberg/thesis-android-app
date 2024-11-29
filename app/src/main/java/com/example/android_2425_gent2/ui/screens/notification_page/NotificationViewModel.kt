package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.notification.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val notificationRepository: NotificationRepository): ViewModel() {

    private val _notificationsUiSate = MutableStateFlow(NotificationsUiState(loading = true))
    val notificationsUiState: StateFlow<NotificationsUiState> = _notificationsUiSate;


    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            notificationRepository.getNotifications()
                .collect { apiResource ->
                    when (apiResource) {
                        is APIResource.Loading -> {
                            _notificationsUiSate.value = NotificationsUiState(loading = true)
                        }
                        is APIResource.Success -> {
                            val response = apiResource.data
                            if (response != null) {
                                val currentList = _notificationsUiSate.value.notifications
                                val newItems = response

                                val combinedList = (currentList + newItems).distinctBy { it.id }

                                _notificationsUiSate.value = NotificationsUiState(
                                    loading = false,
                                    notifications = combinedList
                                )
                            } else {
                                _notificationsUiSate.value = NotificationsUiState(
                                    hasError = true,
                                    errorMessage = "No data available"
                                )
                            }
                        }
                        is APIResource.Error -> {
                            _notificationsUiSate.value = NotificationsUiState(
                                notifications = _notificationsUiSate.value.notifications,
                                hasError = true,
                                errorMessage = apiResource.message
                            )
                        }
                    }
                }
        }
    }
}


data class NotificationsUiState(
    val notifications: List<NotificationDto> = emptyList(),
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)