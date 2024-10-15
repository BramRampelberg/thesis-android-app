package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class TimeSlotEntity(
    @PrimaryKey val id: Int,
    val date: LocalDate,
    val start: LocalTime,
    val end: LocalTime,
)
