package com.example.android_2425_gent2

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android_2425_gent2.di.AppContainer
import com.example.android_2425_gent2.di.AppDataContainer
import com.example.android_2425_gent2.workers.SeedDatabaseWorker

class MainApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        if (BuildConfig.DEBUG) {
            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
            WorkManager.getInstance(this).enqueue(request)
        }
    }
}