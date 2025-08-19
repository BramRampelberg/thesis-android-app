package com.example.android_2425_gent2.data.repository.reservation

import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {

    suspend fun getReservations(
        getPast: Boolean = false,
        getCanceled: Boolean,
    ): Flow<APIResource<List<Reservation>>>

    suspend fun cancelReservation(reservationId: Int): Flow<APIResource<Unit>>

    suspend fun getReservationDetails(reservationId: Int): Flow<APIResource<ReservationDetailsDto>>

}