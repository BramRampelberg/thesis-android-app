package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.Date

class TestNotificationRepository: NotificationRepository {

    override suspend fun getNotifications(): Flow<APIResource<List<Notification>>> = flow {
        val mockNotifications = listOf(
            Notification(
                id = 1,
                severity = 1,
                title = "This is a info notification",
                message = "This is the message",
                timeStamp = Date(),
                isRead = false
            )
        )

        val mockResponse = mockNotifications

        emit(APIResource.Loading(null))
        delay(100)
        emit(APIResource.Success(mockResponse))
    }

    override suspend fun markNotificationAsRead(id: Int): Flow<APIResource<Unit>> = flow {
        emit(APIResource.Loading())
        delay(100) // Simulate network delay
        emit(APIResource.Success(Unit))
    }
}