package com.example.android_2425_gent2.data.repository.reservation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import com.example.android_2425_gent2.data.local.dao.OfflineReservationDao
import com.example.android_2425_gent2.data.local.entity.asExternalModel
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import com.example.android_2425_gent2.data.network.model.asEntity
import com.example.android_2425_gent2.data.network.reservation.ReservationApiService
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class OfflineFirstReservationRepository(
    private val reservationDao: OfflineReservationDao,
    private val remoteApiService: ReservationApiService
) : ReservationRepository {

    /**
     * get reservations from local db
     * fetch from network and update local db
     */
    override suspend fun getReservations(
        cursor: Int?,
        isNextPage: Boolean?,
        getPast: Boolean,
        pageSize: Int
    ): Flow<APIResource<ReservationResponse>> = flow {
        //emit loading
        emit(APIResource.Loading())

        val reservationsFlow = reservationDao.getOfflineReservations(getPast = getPast)
            .distinctUntilChanged()
            .map { localReservations ->
                APIResource.Success(
                    ReservationResponse(
                        data = localReservations.map { it.asExternalModel() },
                        isFirstPage = cursor == null,
                        previousId = cursor,
                        nextId = null
                    )
                )
            }

        //launch network request load in data in local db
        try {
            var currentCursor = cursor
            var hasMorePages = true

            while (hasMorePages) {
                val queryParams = buildMap<String, Any> {
                    currentCursor?.let { put("cursor", it) }
                    isNextPage?.let { put("isNextPage", it) }
                    put("getPast", getPast)
                    put("pageSize", pageSize)
                }
                println("Making API call with queryParams: $queryParams")

                val response = remoteApiService.getReservationPage(queryParams)
                println("Received API response: $response")

                // udpdate local database
                withContext(Dispatchers.IO) {
                    reservationDao.insert(response.data.map { it.asEntity() })
                }

                currentCursor = response.nextId
                hasMorePages = response.nextId != null && response.data.isNotEmpty()

                delay(100)
            }
        } catch (e: Exception) {
            println("Error in getReservations: ${e.message}")
            e.printStackTrace()
            // On  error, we emit error only if local database is empty
            val localData = reservationDao.getOfflineReservations(getPast = getPast).first()
            if (localData.isEmpty()) {
                emit(APIResource.Error("No reservations found"))
                return@flow
            }

        }

        //collect and emit
        reservationsFlow.collect { emission ->
            emit(emission)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertReservation(
        createRemoteReservationRequest: CreateRemoteReservationRequest
    ): Flow<APIResource<Int>> = flow {
        emit(APIResource.Loading())

        val result = withContext(Dispatchers.IO) {
            try {
                val response = remoteApiService.createReservation(createRemoteReservationRequest)
                APIResource.Success(response)
            } catch (e: Exception) {
                APIResource.Error("Failed to create reservation: ${e.message}")
            }
        }

        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getReservationDetails(reservationId: Int): Flow<APIResource<ReservationDetailsDto>> = flow {
        println("Getting reservation details for ID: $reservationId")
        emit(APIResource.Loading())

        val result = withContext(Dispatchers.IO) {
            try {
                println("Making API call for reservation details")
                val response = remoteApiService.getReservationDetails(reservationId)
                
                if (response.isSuccessful && response.body() != null) {
                    println("Received successful API Response: ${response.body()}")
                    APIResource.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error response: $errorBody")
                    try {
                        // Try to parse error message from response
                        val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
                            ?: "Failed to fetch reservation details"
                        APIResource.Error(errorMessage)
                    } catch (e: JsonSyntaxException) {
                        // If parsing fails, return the raw error message
                        APIResource.Error(errorBody ?: "Failed to fetch reservation details")
                    }
                }
            } catch (e: Exception) {
                println("Error getting reservation details: ${e.message}")
                e.printStackTrace()
                APIResource.Error("Failed to fetch reservation details: ${e.message}")
            }
        }

        emit(result)
    }.flowOn(Dispatchers.IO)
}

data class ErrorResponse(
    val message: String?
)