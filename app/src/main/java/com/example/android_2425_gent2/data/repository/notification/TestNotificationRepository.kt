package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class TestNotificationRepository: NotificationRepository {

    override suspend fun getNotifications(): Flow<APIResource<List<NotificationDto>>> = flow {
        val mockNotifications = listOf(
            NotificationDto(
                id = 1,
                severity = 1,
                title = "This is a info notification",
                message = "This is the message",
                createdAt = LocalDateTime.now(),
                isRead = false
            )
        )

        val mockResponse = mockNotifications

        emit(APIResource.Loading(null))
        delay(100)
        emit(APIResource.Success(mockResponse))
    }

    override suspend fun getNotificationDetails(id: Int): Flow<APIResource<NotificationDto>> = flow {
        val mockNotifications = listOf(
            NotificationDto(
                id = 1,
                severity = 1,
                title = "This is a info notification",
                message = "This is a longer message with more details about the notification that would be shown on the details page. It includes additional information that might be relevant to the user.",
                createdAt = LocalDateTime.now(),
                isRead = false
            )
        )

        // Find the notification with the matching id
        val notification = mockNotifications.find { it.id == id }

        emit(APIResource.Loading(null))
        delay(100)

        if (notification != null) {
            emit(APIResource.Success(notification))
        } else {
            emit(APIResource.Error("Notification not found"))
        }
    }
}