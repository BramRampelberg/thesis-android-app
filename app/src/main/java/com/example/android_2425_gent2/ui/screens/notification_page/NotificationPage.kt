package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.partials.NotificationRow

@Composable
fun NotificationPageContent(
    notificationsUiState: NotificationsUiState,
    onNotificationClick: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        notificationsUiState.hasError -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = notificationsUiState.errorMessage
                        ?: stringResource(R.string.something_went_wrong),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        notificationsUiState.loading && notificationsUiState.notifications.isEmpty() -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        notificationsUiState.notifications.isEmpty() -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No notifications",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = modifier.testTag("NotificationPageList"),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notificationsUiState.notifications) { notification ->
                    NotificationRow(
                        notification = notification,
                        onClick = { onNotificationClick(notification) }
                    )
                }
            }
        }
    }
}