package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.network.model.NotificationResponse
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class TestNotificationRepository: NotificationRepository {

    override suspend fun getNotifications(): Flow<APIResource<NotificationResponse>> = flow {
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

        val mockResponse = NotificationResponse(
            notifications = mockNotifications
        )

        emit(APIResource.Loading(null))
        delay(100)
        emit(APIResource.Success(mockResponse))
    }
}