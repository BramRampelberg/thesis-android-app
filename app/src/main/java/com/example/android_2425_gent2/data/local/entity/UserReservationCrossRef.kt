package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "reservationId"], tableName = "user_reservation")
data class UserReservationCrossRef(
    val userId: Int,
    val reservationId: Int,
)
