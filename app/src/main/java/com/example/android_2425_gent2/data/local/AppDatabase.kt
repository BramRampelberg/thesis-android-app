package com.example.android_2425_gent2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_2425_gent2.data.local.dao.ReservationDao
import com.example.android_2425_gent2.data.local.dao.UserDao
import com.example.android_2425_gent2.data.local.entity.BoatEntity
import com.example.android_2425_gent2.data.local.entity.ReservationEntity
import com.example.android_2425_gent2.data.local.entity.TimeSlotEntity
import com.example.android_2425_gent2.data.local.entity.UserEntity
import com.example.android_2425_gent2.data.local.entity.UserReservationCrossRef

@Database(
    entities = [
        UserEntity::class,
        BoatEntity::class,
        ReservationEntity::class,
        TimeSlotEntity::class,
        UserReservationCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reservationDao(): ReservationDao
}

fun getDb(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()
}