package com.example.android_2425_gent2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android_2425_gent2.data.network.model.ReservationDto

@Entity(tableName = "offline_reservation")
data class OfflineReservationEntity(
    @PrimaryKey
    val id: Int,
    val start: String,
    val end: String,
    val date: String,
    val boatId: Int,
    val boatPersonalName: String,
)

fun OfflineReservationEntity.asExternalModel() = ReservationDto(
    id = id,
    start = start,
    end = end,
    date = date,
    boatId = boatId,
    boatPersonalName = boatPersonalName
)
