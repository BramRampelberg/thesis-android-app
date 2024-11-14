package com.example.android_2425_gent2.data.network.reservation

import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationApiService {
    @POST("/api/Reservation")
    suspend fun createReservation(
        @Body reservation: CreateRemoteReservationRequest
    ): Int
}