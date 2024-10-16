package com.example.android_2425_gent2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.android_2425_gent2.data.local.entity.UserEntity
import com.example.android_2425_gent2.data.local.entity.linking_entities.UserWithReservationsEntity

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM userentity WHERE userId = :userId")
    suspend fun getUserWithReservations(userId: Int): UserWithReservationsEntity

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)
}