package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.local.entity.OfflineNotificationEntity
import java.time.ZoneId
import java.util.Date

data class Notification(
    val id: Int,
    val severity: Int,
    val title: String,
    val message: String,
    val timeStamp: Date,
    val isRead: Boolean
)

fun Notification.asEntity() = OfflineNotificationEntity(id = id, severity= severity, title=title, message=message, createdAt = timeStamp.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime(), isRead = isRead)
