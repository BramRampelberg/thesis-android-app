package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.partials.NotificationContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailsPage(
    notificationId: Int,
    onNavigateBack: () -> Unit,
    viewModel: NotificationDetailsViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.notificationDetailsUiState.collectAsState()

    viewModel.loadNotificationDetails(notificationId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.notification?.title ?: "Notification Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else if (uiState.hasError) {
                Text(
                    text = uiState.errorMessage ?: "Something went wrong",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                uiState.notification?.let { notification ->
                    NotificationContent(
                        notification = notification,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}