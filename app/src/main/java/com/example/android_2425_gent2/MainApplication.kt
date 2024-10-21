package com.example.android_2425_gent2

import android.app.Application
import com.example.android_2425_gent2.data.AppContainer
import com.example.android_2425_gent2.data.AppDataContainer

class MainApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}