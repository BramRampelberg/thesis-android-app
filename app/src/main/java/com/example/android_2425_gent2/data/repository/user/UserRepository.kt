package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.model.UserSurface
import com.example.android_2425_gent2.data.network.model.UserSurfaceInfoDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getReservations() : Flow<APIResource<List<UserSurface>>>
}