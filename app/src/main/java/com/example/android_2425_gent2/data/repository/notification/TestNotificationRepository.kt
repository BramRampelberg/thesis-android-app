package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class TestNotificationRepository: NotificationRepository {
    private val mockNotifications = mutableListOf(
        Notification(
            id = 1,
            severity = 1,
            title = "This is a info notification",
            message = "This is the message",
            timeStamp = Date(),
            isRead = false
        ),
        Notification(
            id = 2,
            severity = 1,
            title = "This is a info notification",
            message = "This is the message",
            timeStamp = Date(),
            isRead = false
        )
    )

    override suspend fun getNotifications(): Flow<APIResource<List<Notification>>> = flow {
        emit(APIResource.Loading())
        delay(100)
        emit(APIResource.Success(mockNotifications.toList()))
    }

    override suspend fun markNotificationAsRead(id: Int): Flow<APIResource<Unit>> = flow {
        emit(APIResource.Loading())
        val index = mockNotifications.indexOfFirst { it.id == id }
        if (index != -1) {
            mockNotifications[index] = mockNotifications[index].copy(isRead = true)
        }
        emit(APIResource.Success(Unit))
        // Trigger a refresh by emitting new notifications
        getNotifications().collect { resource ->
            when (resource) {
                is APIResource.Success -> emit(APIResource.Success(Unit))
                is APIResource.Error -> emit(APIResource.Error("Error"))
                is APIResource.Loading -> emit(APIResource.Loading())
            }
        }
    }
}