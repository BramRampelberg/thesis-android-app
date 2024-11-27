package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.local.entity.OfflineNotificationEntity
import com.example.android_2425_gent2.data.test_data.getTestNotifications
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId

class NotificationTest {
    val notification = getTestNotifications()[0]

    @Test
    fun notificationAsEntity_returnsNotificationEntity() {
        val notificationEntity = notification.asEntity()

        assertEquals(
            notificationEntity, OfflineNotificationEntity(
                id = notification.id,
                title = notification.title,
                message = notification.message,
                severity = notification.severity,
                isRead = notification.isRead,
                createdAt = notification.timeStamp.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            )
        )
    }
}