package com.example.android_2425_gent2.data.network.model

import com.example.android_2425_gent2.data.local.entity.OfflineReservationEntity
import com.example.android_2425_gent2.data.model.Reservation
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

data class ReservationDto(
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate,
    val boatId: Int,
    val boatPersonalName: String,
    val id: Int
)



fun ReservationDto.asEntity() = OfflineReservationEntity(
    start = start,
    end = end,
    date = date,
    boatId = boatId,
    boatPersonalName = boatPersonalName,
    id = id,
)