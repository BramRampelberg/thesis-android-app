package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.local.entity.OfflineReservationEntity
import com.example.android_2425_gent2.data.network.model.ReservationDto
import java.time.LocalDate
import java.time.LocalTime

data class OfflineReservation (
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate,
    val boatId: Int,
    val boatPersonalName: String,
    val id: Int
)




