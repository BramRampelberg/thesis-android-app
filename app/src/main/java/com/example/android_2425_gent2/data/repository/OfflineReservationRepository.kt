package com.example.android_2425_gent2.data.repository

import com.example.android_2425_gent2.data.local.dao.ReservationDao
import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow

class OfflineReservationRepository(private val reservationDao: ReservationDao) :
    ReservationRepository {
    override fun getReservationsByUserStream(userId: Int): Flow<List<ReservationEntity>> =
        reservationDao.getReservationsByUser(userId)

    override fun getReservationStream(id: Int): Flow<ReservationEntity?> =
        reservationDao.getReservationById(id)

    override suspend fun insertReservation(reservation: ReservationEntity) =
        reservationDao.insert(reservation)

    override suspend fun deleteReservation(reservation: ReservationEntity) =
        reservationDao.delete(reservation)

    override suspend fun updateReservation(reservation: ReservationEntity) =
        reservationDao.update(reservation)
}