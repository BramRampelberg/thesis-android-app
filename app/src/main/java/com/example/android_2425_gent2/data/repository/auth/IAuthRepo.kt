package com.example.android_2425_gent2.data.repository.auth

import com.auth0.android.result.Credentials
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow


interface IAuthRepo {
    suspend fun getStoredCredentials(): Flow<APIResource<Credentials>>

    suspend fun login(userName: String, password: String): Flow<APIResource<Credentials>>
}