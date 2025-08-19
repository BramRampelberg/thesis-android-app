package com.example.android_2425_gent2.data.repository.reservation

import android.util.Log
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import com.example.android_2425_gent2.data.network.model.ReservationDto
import com.example.android_2425_gent2.data.network.reservation.ReservationApiService
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReservationRepositoryImpl(
    private val remoteApiService: ReservationApiService
) : ReservationRepository {
    private val pageSize = 20

    override suspend fun getReservations(
        getPast: Boolean,
        getCanceled: Boolean,
    ): Flow<APIResource<List<Reservation>>> =
        flow {
            emit(APIResource.Loading())
            try {
                val reservations = ArrayList<ReservationDto>()
                var currentCursor: Int? = null
                var hasMorePages = true

                while (hasMorePages) {
                    val queryParams = buildMap<String, Any> {
                        currentCursor?.let { put("cursor", it) }
                        put("getPast", getPast)
                        put("pageSize", pageSize)
                        put("isNextPage", true)
                        put("canceled", getCanceled)
                    }

                    val response = remoteApiService.getReservationPage(queryParams)
                    reservations.addAll(response.data)
                    emit(APIResource.Success(reservations.map { dto ->
                        Reservation.fromDto(dto)
                    }.toList()))

                    currentCursor = response.nextId
                    hasMorePages = response.nextId != null && response.data.isNotEmpty()

                    delay(100)
                }
            } catch (e: Exception) {
                Log.e("ReservationsError", "Failed to fetch reservations: ${e.message}")
                emit(APIResource.Error("Failed to fetch reservations: ${e.message}"))
            }

        }.flowOn(Dispatchers.IO)


    override suspend fun getReservationDetails(reservationId: Int): Flow<APIResource<ReservationDetailsDto>> =
        flow {
            emit(APIResource.Loading())
            try {
                val response = remoteApiService.getReservationDetails(reservationId)
                if (response.isSuccessful && response.body() != null) {
                    val details = response.body()!!

                    emit(APIResource.Success(details))
                } else {
                    emit(APIResource.Error("Failed to fetch reservation details"))
                }
            } catch (e: Exception) {
                emit(APIResource.Error("Failed to fetch reservation details: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun cancelReservation(reservationId: Int): Flow<APIResource<Unit>> =
        flow {
            emit(APIResource.Loading(Unit))

            try {
                val response = remoteApiService.cancelReservation(reservationId)

                if (response.isSuccessful) {
                    emit(APIResource.Success(Unit))
                } else {
                    emit(APIResource.Error("Failed to cancel reservation"))
                }
            } catch (e: Exception) {
                emit(APIResource.Error("Failed to cancel reservation: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
}

