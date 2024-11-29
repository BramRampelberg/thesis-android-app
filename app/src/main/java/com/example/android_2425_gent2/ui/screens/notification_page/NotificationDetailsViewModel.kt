package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.notification.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationDetailsViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notificationDetailsUiState = MutableStateFlow(NotificationDetailsUiState())
    val notificationDetailsUiState: StateFlow<NotificationDetailsUiState> = _notificationDetailsUiState

    fun loadNotificationDetails(notificationId: Int) {
        viewModelScope.launch {
            notificationRepository.getNotificationDetails(notificationId)
                .collect { apiResource ->
                    when (apiResource) {
                        is APIResource.Loading -> {
                            _notificationDetailsUiState.value = NotificationDetailsUiState(isLoading = true)
                        }
                        is APIResource.Success -> {
                            _notificationDetailsUiState.value = NotificationDetailsUiState(
                                notification = apiResource.data
                            )
                        }
                        is APIResource.Error -> {
                            _notificationDetailsUiState.value = NotificationDetailsUiState(
                                hasError = true,
                                errorMessage = apiResource.message
                            )
                        }
                    }
                }
        }
    }
}

data class NotificationDetailsUiState(
    val notification: NotificationDto? = null,
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)