package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.local.entity.OfflineReservationEntity
import com.example.android_2425_gent2.data.network.model.ReservationDto

data class OfflineReservation (
    val id: Int,
    val start: String,
    val end: String,
    val date: String,
    val boatId: Int,
    val boatPersonalName: String
)



