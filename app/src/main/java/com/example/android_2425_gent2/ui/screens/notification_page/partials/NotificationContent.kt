package com.example.android_2425_gent2.ui.screens.notification_page.partials

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.extensions.formatRelative

@Composable
fun NotificationContent(
    notification: Notification,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    getSeverityIcon(notification.severity),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = getSeverityColor(notification.severity)
                )
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("NotificationDetailsTitle")
                )
            }

            Text(
                text = notification.timeStamp.formatRelative(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("NotificationDetailsTimestamp")
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = notification.message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.testTag("NotificationDetailsMessage")
        )
    }
}

@Composable
private fun getSeverityIcon(severity: Int): ImageVector {
    return when (severity) {
        1 -> Icons.Rounded.Info // Info
        2 -> Icons.Rounded.CheckCircle // Success
        3 -> Icons.Rounded.Warning // Warning
        4 -> Icons.Rounded.Warning // Error
        else -> Icons.Rounded.Info // Default
    }
}

@Composable
private fun getSeverityColor(severity: Int): Color {
    return when (severity) {
        1 -> colorResource(id = R.color.info) // Info
        2 -> colorResource(id = R.color.success) // Success
        3 -> colorResource(id = R.color.warning) // Warning
        4 -> colorResource(id = R.color.error) // Error
        else -> colorResource(id = R.color.info) // Default
    }
}