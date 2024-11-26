package com.example.android_2425_gent2.ui.screens.notification_page.partials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.extensions.formatRelative
import java.util.Date

@Preview
@Composable
fun NotificationRow(notification: Notification = Notification(
    id = 1,
    severity = 1,
    title = "Test Title, this is a realy long title so",
    message = "Test message content goes here. It is a realy long message",
    timeStamp = Date(),
    isRead = false
)) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Top row with icon, title and time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        getSeverityIcon(notification.severity),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = getSeverityColor(notification.severity)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (!notification.isRead) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(colorResource(id = R.color.info), CircleShape)
                    )
                }
                Text(
                    text = notification.timeStamp.formatRelative(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )

            }

            // Message
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
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