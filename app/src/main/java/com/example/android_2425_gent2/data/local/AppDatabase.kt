package com.example.android_2425_gent2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android_2425_gent2.data.local.dao.OfflineReservationDao
import com.example.android_2425_gent2.data.local.entity.OfflineReservationEntity

@Database(
    entities = [
        OfflineReservationEntity::class,
    ],
    exportSchema = false,
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database",
                ).build().also { Instance = it }
            }

        }
    }

    abstract fun offlineReservationDao(): OfflineReservationDao
}
