package com.example.android_2425_gent2.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.mock_data.getMockReservations
import com.example.android_2425_gent2.data.mock_data.getMockTimeSlots
import com.example.android_2425_gent2.data.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = AppDatabase.getDatabase(applicationContext)
            val reservationDao = database.reservationDao()
            val timeSlotDao = database.timeSlotDao()

            database.clearAllTables()

            launch {
                reservationDao.insert(getMockReservations().map { it.toEntity() })
                timeSlotDao.insert(getMockTimeSlots().map { it.toEntity() })
            }

            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}