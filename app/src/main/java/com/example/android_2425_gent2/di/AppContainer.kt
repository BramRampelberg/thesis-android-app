package com.example.android_2425_gent2.di

import android.content.Context
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.repository.notification.NotificationRepository
import com.example.android_2425_gent2.data.repository.notification.OfflineFirstNotificationRepository
import com.example.android_2425_gent2.data.repository.notification.TestNotificationRepository
import com.example.android_2425_gent2.data.repository.reservation.OfflineFirstReservationRepository
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import com.example.android_2425_gent2.data.repository.reservation.TestReservationRepository
import com.example.android_2425_gent2.data.repository.timeslot.NetworkTimeSlotRepository
import com.example.android_2425_gent2.data.repository.timeslot.TestTimeSlotRepository
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.di.module.NetworkModule

interface AppContainer {
    val reservationRepository: ReservationRepository
    val timeSlotRepository: TimeSlotRepository
    val notificationRepository: NotificationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val reservationRepository: ReservationRepository by lazy {
        OfflineFirstReservationRepository(AppDatabase.getDatabase(context).offlineReservationDao()
        , NetworkModule.reservationApiService)
    }

    override val timeSlotRepository: TimeSlotRepository by lazy {
        NetworkTimeSlotRepository(NetworkModule.timeSlotApiService)
    }

    override val notificationRepository: NotificationRepository by lazy {
        OfflineFirstNotificationRepository(AppDatabase.getDatabase(context).offlineNotificationDao(),
            NetworkModule.notificationApiService)
    }
}

class TestContainer() : AppContainer {
    override val reservationRepository: ReservationRepository by lazy {
        TestReservationRepository()
    }
    override val timeSlotRepository: TimeSlotRepository by lazy{
        TestTimeSlotRepository()
    }
    override val notificationRepository: NotificationRepository by lazy {
        TestNotificationRepository()
    }
}
