package com.example.android_2425_gent2.data.repository.notification

import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.network.model.NotificationResponse
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications(): Flow<APIResource<List<NotificationDto>>>
}