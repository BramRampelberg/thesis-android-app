package com.example.android_2425_gent2.network // Adjust the package name accordingly

import com.example.android_2425_gent2.data.remote.model.Reservation

import retrofit2.http.GET

interface ApiService {
    @GET("api/Reservation/user/2")
    suspend fun getReservations(): List<Reservation>

}


