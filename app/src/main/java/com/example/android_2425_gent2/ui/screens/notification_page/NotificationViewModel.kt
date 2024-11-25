package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.mock_data.getMockNotifications
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.ReservationDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel: ViewModel() {

    private val _notificationsUiSate = MutableStateFlow(NotificationsUiState(loading = true))
    val notificationsUiState: StateFlow<NotificationsUiState> = _notificationsUiSate;


    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {  // Add this
            _notificationsUiSate.value = NotificationsUiState(
                reservations = getMockNotifications(),
                loading = false,
                hasError = false
            )
        }
    }
}


data class NotificationsUiState(
    val reservations: List<Notification> = emptyList(),
    val loading: Boolean = false,
    val hasError: Boolean = false
)