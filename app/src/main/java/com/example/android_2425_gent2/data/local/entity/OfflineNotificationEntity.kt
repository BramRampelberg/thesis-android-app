package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android_2425_gent2.data.network.model.NotificationDto
import java.time.LocalDateTime

@Entity(tableName = "offline_notification")
data class OfflineNotificationEntity(
    @PrimaryKey
    val id: Int,
    val severity: Int,
    val title: String,
    val message: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean
)

fun OfflineNotificationEntity.asExternalModel() = NotificationDto(
    id = id,
    severity = severity,
    title = title,
    message = message,
    createdAt = createdAt,
    isRead = isRead
)