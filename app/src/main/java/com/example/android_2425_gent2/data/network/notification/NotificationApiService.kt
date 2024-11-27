package com.example.android_2425_gent2.data.network.notification

import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.data.network.model.NotificationResponse
import retrofit2.http.GET

interface NotificationApiService {
    @GET("/api/Notification/me")
    suspend fun getNotifications(): List<NotificationDto>
}