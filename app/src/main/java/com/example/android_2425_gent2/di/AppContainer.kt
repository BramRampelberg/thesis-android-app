package com.example.android_2425_gent2.di

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.example.android_2425_gent2.data.repository.auth.Auth0Repo
import com.example.android_2425_gent2.data.repository.auth.IAuthRepo
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepositoryImpl
import com.example.android_2425_gent2.data.repository.user.RemoteUserRepository
import com.example.android_2425_gent2.data.repository.user.UserRepository
import com.example.android_2425_gent2.di.module.NetworkModule


interface AppContainer {
    val reservationRepository: ReservationRepository
    val authRepo: IAuthRepo
    val userRepository: UserRepository
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
        ReservationRepositoryImpl(
            NetworkModule.provideReservationApiService(authRepo)
        )
    }
    override val userRepository: UserRepository by lazy {
        RemoteUserRepository(NetworkModule.provideUserApiSerivce(authRepo))
    }
}
