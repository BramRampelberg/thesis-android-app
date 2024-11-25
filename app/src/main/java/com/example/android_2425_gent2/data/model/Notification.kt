package com.example.android_2425_gent2.data.model

import java.util.Date

data class Notification(
    val id: Int,
    val severity: String,
    val title: String,
    val message: String,
    val timeStamp: Date,
    val isRead: Boolean
)