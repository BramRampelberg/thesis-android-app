package com.example.android_2425_gent2.data.network.model

import android.os.Parcelable
import com.example.android_2425_gent2.data.local.entity.OfflineNotificationEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class NotificationDto(
    val severity: Int,
    val title: String,
    val message: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean,
    val id: Int
) : Parcelable

fun NotificationDto.asEntity() = OfflineNotificationEntity(
    severity = severity,
    title = title,
    message = message,
    createdAt = createdAt,
    isRead = isRead,
    id = id
)