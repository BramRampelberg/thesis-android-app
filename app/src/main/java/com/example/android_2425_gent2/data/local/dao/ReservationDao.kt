package com.example.android_2425_gent2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Transaction
    @Query(
        """
        SELECT * 
        FROM reservation r 
        JOIN user_reservation u_r ON r.reservationId = u_r.reservationId 
        WHERE u_r.userId = :userId
        """
    )
    fun getReservationsByUser(userId: Int): Flow<List<ReservationEntity>>

    @Query("SELECT * FROM reservation r WHERE r.reservationId = :id")
    fun getReservationById(id: Int): Flow<ReservationEntity?>

    @Insert
    suspend fun insert(reservation: ReservationEntity)

    @Delete
    suspend fun delete(reservation: ReservationEntity)

    @Update
    suspend fun update(reservation: ReservationEntity)
}