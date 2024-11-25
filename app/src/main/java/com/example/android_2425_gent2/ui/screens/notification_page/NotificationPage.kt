package com.example.android_2425_gent2.ui.screens.notification_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.mock_data.getMockNotifications
import com.example.android_2425_gent2.ui.screens.notification_page.partials.NotificationRow

@Preview
@Composable
fun NotificationPage(modifier: Modifier = Modifier) {
    val notifications = getMockNotifications()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notifications) { notification ->
            NotificationRow(notification)
        }
    }
}