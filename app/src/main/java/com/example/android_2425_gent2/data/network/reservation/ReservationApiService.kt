package com.example.android_2425_gent2.data.network.reservation

import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ReservationApiService {
    @POST("/api/Reservation")
    suspend fun createReservation(
        @Body reservation: CreateRemoteReservationRequest
    ): Int

    @GET("/api/Reservation/me")
    suspend fun getReservationPage(
        @QueryMap queryParams: Map<String, @JvmSuppressWildcards Any>
    ): ReservationResponse
}