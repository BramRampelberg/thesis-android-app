package com.example.android_2425_gent2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.android_2425_gent2.data.local.entity.UserEntity
import com.example.android_2425_gent2.data.local.entity.linking_entities.UserWithReservationsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserWithReservations(userId: Int): Flow<UserWithReservationsEntity>

    @Insert
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}