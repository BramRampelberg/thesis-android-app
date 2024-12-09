package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.partials.NotificationContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailsPage(
    notification: Notification,
    onNavigateBack: () -> Unit,
    viewModel: NotificationDetailsViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.notificationDetailsUiState.collectAsState()

    LaunchedEffect(notification) {
        viewModel.setNotification(notification)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = notification.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = modifier.testTag("notification_details_back_button_${notification.id}")) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("notification_details_page")
        ) {
            if (uiState.hasError) {
                Text(
                    text = uiState.errorMessage ?: stringResource(R.string.something_went_wrong),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                NotificationContent(
                    notification = notification,
                    modifier = Modifier.padding(16.dp).testTag("notification_content_${notification.id}")
                )
            }
        }
    }
}