package com.example.android_2425_gent2.data.repository.auth

import com.auth0.android.result.Credentials
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

// TODO mimic auth repo
class TestAuth0Repo: IAuthRepo {
    override suspend fun getStoredCredentials(): Flow<APIResource<Credentials>> {
        TODO("Not yet implemented")
    }

    override suspend fun login(userName: String, password: String): Flow<APIResource<Credentials>> {
        TODO("Not yet implemented")
    }
}