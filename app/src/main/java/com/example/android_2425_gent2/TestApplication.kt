package com.example.android_2425_gent2

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android_2425_gent2.di.AppContainer
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.TestViewModelProvider
import com.example.android_2425_gent2.ui.ViewModelFactoryProvider
import com.example.android_2425_gent2.workers.TestSeedDatabaseWorker

class TestApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = TestContainer(this)
        ViewModelFactoryProvider.Factory = TestViewModelProvider.Factory

        if (BuildConfig.DEBUG) {
            val request = OneTimeWorkRequestBuilder<TestSeedDatabaseWorker>().build()
            WorkManager.getInstance(this).enqueue(request)
        }
    }
}