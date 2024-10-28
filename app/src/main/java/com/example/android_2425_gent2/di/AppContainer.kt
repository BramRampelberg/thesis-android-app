package com.example.android_2425_gent2.di

import android.content.Context
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.repository.OfflineReservationRepository
import com.example.android_2425_gent2.data.repository.ReservationRepository
import com.example.android_2425_gent2.data.repository.timeslot.NetworkTimeSlotRepository
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.di.module.NetworkModule
import com.example.android_2425_gent2.data.repository.TestReservationRepository

interface AppContainer {
    val reservationRepository: ReservationRepository
    val timeSlotRepository: TimeSlotRepository

}

class AppDataContainer(private val context: Context) : AppContainer {

    override val reservationRepository: ReservationRepository by lazy {
        OfflineReservationRepository(AppDatabase.getDatabase(context).reservationDao())
    }

    override val timeSlotRepository: TimeSlotRepository by lazy {
        NetworkTimeSlotRepository(NetworkModule.timeSlotApiService)
    }
}

class TestContainer(private val context: Context) : AppContainer {
    override val reservationRepository: ReservationRepository by lazy {
        TestReservationRepository()
    }
}
