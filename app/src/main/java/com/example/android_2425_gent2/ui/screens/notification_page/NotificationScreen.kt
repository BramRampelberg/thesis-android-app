package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.ui.AppViewModelProvider

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val selectedNotification by viewModel.selectedNotification.collectAsState()

    if (selectedNotification != null) {
        NotificationDetailsPage(
            notification = selectedNotification!!,
            onNavigateBack = { viewModel.clearSelectedNotification() },
            modifier = modifier
        )
    } else {
        NotificationPage(
            onNotificationClick = { notification ->
                viewModel.selectNotification(notification)
            },
            viewModel = viewModel,
            modifier = modifier
        )
    }
}