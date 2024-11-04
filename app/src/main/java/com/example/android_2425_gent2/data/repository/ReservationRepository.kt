package com.example.android_2425_gent2.data.repository

import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getReservationsByUserStream(userId: Int): Flow<List<Reservation>>

    fun getAllReservationsStream(): Flow<List<Reservation>>

    fun getAllUpcomingReservationsStream(): Flow<List<Reservation>>

    fun getAllPastReservationsStream(): Flow<List<Reservation>>

    fun getReservationStream(id: Int): Flow<Reservation?>

    suspend fun insertReservation(createRemoteReservationRequest: CreateRemoteReservationRequest): APIResource<Int>

    suspend fun deleteReservation(reservation: Reservation)

    suspend fun updateReservation(reservation: Reservation)
}