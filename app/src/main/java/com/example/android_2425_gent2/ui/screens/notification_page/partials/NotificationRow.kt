package com.example.android_2425_gent2.ui.screens.notification_page.partials

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.model.Notification

@Composable
fun NotificationRow(notification: Notification) {
    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()) {
        Text(notification.title, modifier = Modifier.padding(10.dp))
    }
}