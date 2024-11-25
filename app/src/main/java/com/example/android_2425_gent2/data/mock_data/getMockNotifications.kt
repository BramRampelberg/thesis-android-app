package com.example.android_2425_gent2.data.mock_data

import com.example.android_2425_gent2.data.model.Notification
import java.util.Date

fun getMockNotifications(): List<Notification> {
    val date = Date()
    return (1..20).map {
        Notification(
            id = it,
            severity = "Warning",
            title = "Title " + it,
            message = "Message",
            timeStamp = date,
            isRead = true
        )
    }.toList<Notification>()
}