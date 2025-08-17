package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto

data class ReservationDetails(
    val mentorName: String?,
    val batteryId: Int?,
    val currentBatteryUserName: String?,
    val currentBatteryUserId: Int?,
    val currentHolderPhoneNumber: String?,
    val currentHolderEmail: String?,
    val currentHolderStreet: String?,
    val currentHolderNumber: String?,
    val currentHolderCity: String?,
    val currentHolderPostalCode: String?
) {
    companion object {
        fun fromDto(dto: ReservationDetailsDto): ReservationDetails = ReservationDetails(
            dto.mentorName,
            dto.batteryId,
            dto.currentBatteryUserName,
            dto.currentBatteryUserId,
            dto.currentHolderPhoneNumber,
            dto.currentHolderEmail,
            dto.currentHolderStreet,
            dto.currentHolderNumber,
            dto.currentHolderCity,
            dto.currentHolderPostalCode
        )
    }
}
