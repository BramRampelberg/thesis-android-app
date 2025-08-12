package com.example.android_2425_gent2.data.network.reservation

import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ReservationApiService {
    @GET("/api/Reservation/me")
    suspend fun getReservationPage(
        @QueryMap queryParams: Map<String, @JvmSuppressWildcards Any>
    ): ReservationResponse

    @PATCH("/api/Reservation/cancel/{id}")
    suspend fun cancelReservation(
        @Path("id") reservationId: Int
    ): Response<Unit>

    @GET("/api/Reservation/{id}")
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    suspend fun getReservationDetails(
        @Path("id") reservationId: Int
    ): Response<ReservationDetailsDto>
}