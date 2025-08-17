package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.network.model.ReservationDto
import java.time.LocalDate
import java.time.LocalTime

data class Reservation(
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate,
    val boatId: Int,
    val boatPersonalName: String,
    val id: Int,
    val isDeleted: Boolean = false
) {
    companion object {
        fun fromDto(dto: ReservationDto): Reservation = Reservation(
            dto.start,
            dto.end,
            dto.date,
            dto.boatId,
            dto.boatPersonalName,
            dto.id,
            dto.isDeleted
        )
    }
}
