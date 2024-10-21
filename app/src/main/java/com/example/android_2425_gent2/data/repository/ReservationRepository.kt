package com.example.android_2425_gent2.data.repository

import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getReservationsByUserStream(userId: Int): Flow<List<ReservationEntity>>

    fun getReservationStream(id: Int): Flow<ReservationEntity?>

    suspend fun insertReservation(reservation: ReservationEntity)

    suspend fun deleteReservation(reservation: ReservationEntity)

    suspend fun updateReservation(reservation: ReservationEntity)
}