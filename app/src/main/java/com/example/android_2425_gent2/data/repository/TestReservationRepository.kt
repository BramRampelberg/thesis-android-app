package com.example.android_2425_gent2.data.repository

import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.test_data.getTestReservations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TestReservationRepository : ReservationRepository {
    override fun getReservationsByUserStream(userId: Int): Flow<List<Reservation>> =
        listOf(listOf<Reservation>()).asFlow()

    override fun getAllReservationsStream(): Flow<List<Reservation>> =
        listOf(getTestReservations()).asFlow()

    override fun getAllUpcomingReservationsStream(): Flow<List<Reservation>> =
        listOf(listOf(getTestReservations()[2])).asFlow()

    override fun getAllPastReservationsStream(): Flow<List<Reservation>> =
        listOf(listOf(getTestReservations()[1])).asFlow()

    override fun getReservationStream(id: Int): Flow<Reservation?> =
        listOf(getTestReservations().filter { it.id == id }.first()).asFlow()

    override suspend fun insertReservation(reservation: Reservation) {

    }

    override suspend fun deleteReservation(reservation: Reservation) {

    }

    override suspend fun updateReservation(reservation: Reservation) {

    }
}