package com.example.android_2425_gent2.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.example.android_2425_gent2.di.module.NetworkModule
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.repository.OfflineReservationRepository
import com.example.android_2425_gent2.data.repository.ReservationRepository
import com.example.android_2425_gent2.data.repository.timeslot.NetworkTimeSlotRepository
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.data.repository.TestReservationRepository
import com.example.android_2425_gent2.data.repository.timeslot.TestTimeSlotRepository
import com.example.android_2425_gent2.data.repository.auth.IAuthRepo
import com.example.android_2425_gent2.data.repository.auth.Auth0Repo
import com.example.android_2425_gent2.data.repository.auth.TestAuth0Repo

interface AppContainer {
    val reservationRepository: ReservationRepository
    val timeSlotRepository: TimeSlotRepository
    val authRepo: IAuthRepo
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val auth0: Auth0 = Auth0.getInstance(context)
    private val authentication: AuthenticationAPIClient = AuthenticationAPIClient(auth0)
    private val credentialsManager = SecureCredentialsManager(
        context,
        auth0,
        SharedPreferencesStorage(context)
    )

    override val authRepo: IAuthRepo by lazy {
        Auth0Repo(authentication, credentialsManager)
    }

    override val reservationRepository: ReservationRepository by lazy {
        OfflineReservationRepository(AppDatabase.getDatabase(context).reservationDao()
        , NetworkModule.reservationApiService)
    }

    override val timeSlotRepository: TimeSlotRepository by lazy {
        NetworkTimeSlotRepository(NetworkModule.timeSlotApiService)
    }
}

class TestContainer() : AppContainer {

    override val authRepo: IAuthRepo by lazy {
        TestAuth0Repo()
    }

    override val reservationRepository: ReservationRepository by lazy {
        TestReservationRepository()
    }
    override val timeSlotRepository: TimeSlotRepository by lazy {
        TestTimeSlotRepository()
    }
}
