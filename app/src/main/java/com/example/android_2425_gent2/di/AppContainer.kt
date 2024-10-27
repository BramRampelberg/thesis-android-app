package com.example.android_2425_gent2.di

import android.content.Context
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.repository.OfflineReservationRepository
import com.example.android_2425_gent2.data.repository.ReservationRepository
import com.example.android_2425_gent2.data.repository.TestReservationRepository

interface AppContainer {
    val reservationRepository: ReservationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val reservationRepository: ReservationRepository by lazy {
        OfflineReservationRepository(AppDatabase.getDatabase(context).reservationDao())
    }
}

class TestContainer(private val context: Context) : AppContainer {
    override val reservationRepository: ReservationRepository by lazy {
        TestReservationRepository()
    }
}
