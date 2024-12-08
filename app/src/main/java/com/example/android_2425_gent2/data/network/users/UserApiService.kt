package com.example.android_2425_gent2.data.network.users

import com.example.android_2425_gent2.data.network.model.PaginatedResponse
import com.example.android_2425_gent2.data.network.model.UserSurfaceInfoDto
import retrofit2.http.GET


interface UserApiService {
    @GET("/api/User?role=Guest")
    suspend fun getGuests(): PaginatedResponse<UserSurfaceInfoDto>
}