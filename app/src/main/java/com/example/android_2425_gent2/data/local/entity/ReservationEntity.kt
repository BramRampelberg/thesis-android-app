package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val reservationId: Int,
    val boatId: Int,
    val timeSlotId: Int,
)
