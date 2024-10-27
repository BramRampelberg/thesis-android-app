package com.example.android_2425_gent2.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.model.asEntity
import com.example.android_2425_gent2.data.test_data.getTestReservations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestSeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = AppDatabase.getDatabase(applicationContext)
            val reservationDao = database.reservationDao()
            val timeSlotDao = database.timeSlotDao()
            val boatDao = database.boatDao()

            database.clearAllTables()

            val reservations = getTestReservations()

            launch {
                reservationDao.insert(reservations.map { it.asEntity() })
                timeSlotDao.insert(reservations.map { it.timeSlot }.filter { it != null }
                    .map { it!!.asEntity() })
                boatDao.insert(reservations.map { it.boat }.filter { it != null }
                    .map { it!!.asEntity() })
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