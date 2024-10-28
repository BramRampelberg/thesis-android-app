package com.example.android_2425_gent2.data.repository

import com.example.android_2425_gent2.data.local.dao.ReservationDao
import com.example.android_2425_gent2.data.local.entity.asExternalModel
import com.example.android_2425_gent2.data.local.entity.linking_entities.asExternalModel
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.map

class OfflineReservationRepository(private val reservationDao: ReservationDao) :
    ReservationRepository {
    override fun getReservationsByUserStream(userId: Int): Flow<List<Reservation>> =
        reservationDao.getReservationsByUser(userId)
            .map { it -> it.map { it.asExternalModel() } }

    override fun getAllReservationsStream(): Flow<List<Reservation>> =
        reservationDao.getAllReservations().map { it -> it.map { it.asExternalModel() } }

    override fun getAllUpcomingReservationsStream(): Flow<List<Reservation>> =
        reservationDao.getAllReservationsAfterDateAndTime(LocalDate.now(), LocalTime.now())
            .map { it -> it.map { it.asExternalModel() } }

    override fun getAllPastReservationsStream(): Flow<List<Reservation>> =
        reservationDao.getAllReservationsBeforeDateAndTime(LocalDate.now(), LocalTime.now())
            .map { it -> it.map { it.asExternalModel() } }

    override fun getReservationStream(id: Int): Flow<Reservation?> =
        reservationDao.getReservationById(id).map { it?.asExternalModel() }

    override suspend fun insertReservation(reservation: Reservation) =
        reservationDao.insert(reservation.asEntity())

    override suspend fun deleteReservation(reservation: Reservation) =
        reservationDao.delete(reservation.asEntity())

    override suspend fun updateReservation(reservation: Reservation) =
        reservationDao.update(reservation.asEntity())
}