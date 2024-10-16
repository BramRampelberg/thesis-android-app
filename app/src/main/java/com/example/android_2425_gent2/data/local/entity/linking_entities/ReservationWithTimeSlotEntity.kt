package com.example.android_2425_gent2.data.local.entity.linking_entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import com.example.android_2425_gent2.data.local.entity.TimeSlotEntity


data class ReservationWithTimeSlotEntity(
    @Embedded val timeSlot: TimeSlotEntity,
    @Relation(
        parentColumn = "reservationId",
        entityColumn = "timeSlotId",
    )
    val reservation: ReservationEntity
)
