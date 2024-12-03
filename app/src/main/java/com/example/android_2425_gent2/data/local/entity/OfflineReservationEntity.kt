package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android_2425_gent2.data.model.OfflineReservation
import com.example.android_2425_gent2.data.network.model.ReservationDto
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "offline_reservation")
data class OfflineReservationEntity(
    @PrimaryKey
    val id: Int,
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate,
    val boatId: Int,
    val boatPersonalName: String,
)

fun OfflineReservationEntity.asExternalModel() = OfflineReservation(
    id = id,
    start = start,
    end = end,
    date = date,
    boatId = boatId,
    boatPersonalName = boatPersonalName
)
