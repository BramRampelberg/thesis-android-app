package com.example.android_2425_gent2.data.network.users

import com.example.android_2425_gent2.data.network.model.UserDetailsDto
import retrofit2.http.GET


interface UserApiService {
    @GET("/api/User/profile")
    suspend fun getUserDetails(): UserDetailsDto
}