package com.example.android_2425_gent2.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.android_2425_gent2.data.local.entity.OfflineReservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineReservationDao : EntityDao<OfflineReservationEntity>{
    @Transaction
    @Query("SELECT * FROM offline_reservation")
    fun getOfflineReservations(): Flow<OfflineReservationEntity>

}