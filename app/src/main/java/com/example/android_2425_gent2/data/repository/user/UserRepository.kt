package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.network.model.UserDetailsDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserDetails(): Flow<APIResource<UserDetailsDto>>
}