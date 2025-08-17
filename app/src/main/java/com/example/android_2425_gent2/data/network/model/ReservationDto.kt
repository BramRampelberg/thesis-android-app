package com.example.android_2425_gent2.data.network.model

import java.time.LocalDate
import java.time.LocalTime

data class ReservationDto(
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate,
    val boatId: Int,
    val boatPersonalName: String,
    val id: Int,
    val isDeleted: Boolean = false
)
