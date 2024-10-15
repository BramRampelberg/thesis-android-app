package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReservationEntity(
    @PrimaryKey val id: Int,
    val boatId: Int,
    val timeSlotId: Int,
)
