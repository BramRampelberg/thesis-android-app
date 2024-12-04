package com.example.android_2425_gent2.data.network.notification

import com.example.android_2425_gent2.data.network.model.NotificationDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApiService {
    @GET("/api/Notification/me")
    suspend fun getNotifications(): List<NotificationDto>

    @GET("notifications/{id}")
    suspend fun getNotificationDetails(@Path("id") id: Int): NotificationDto
}