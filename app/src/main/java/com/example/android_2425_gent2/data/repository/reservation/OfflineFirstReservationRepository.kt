package com.example.android_2425_gent2.data.repository.reservation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import com.example.android_2425_gent2.data.local.dao.OfflineReservationDao
import com.example.android_2425_gent2.data.local.entity.asExternalModel
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import com.example.android_2425_gent2.data.network.model.asEntity
import com.example.android_2425_gent2.data.network.reservation.ReservationApiService
import com.example.android_2425_gent2.data.repository.APIResource
class OfflineFirstReservationRepository(
    private val reservationDao: OfflineReservationDao,
    private val remoteApiService: ReservationApiService
) : ReservationRepository {

    /**
     * Get reservation from local db
     * then get from online and update local db
     */

    override suspend fun getReservations(
        cursor: Int?,
        isNextPage: Boolean?,
        getPast: Boolean,
        pageSize: Int
    ): Flow<APIResource<ReservationResponse>> = flow {
        emit(APIResource.Loading())

        // Get filtered data from local db first
        val localReservations = withContext(Dispatchers.IO) {
            reservationDao.getOfflineReservations(getPast = getPast).first()
        }
        println("local reservations ${localReservations}")
        println("local data empty: ${localReservations.isEmpty()}")

        if (localReservations.isNotEmpty()) {
            emit(APIResource.Success(ReservationResponse(
                data = localReservations.map { it.asExternalModel() },
                isFirstPage = true,
                previousId = null,
                nextId = null
            )))
        }

        // Try to get online data
        try {
            println("try to get online data")
            val queryParams = buildMap<String, Any> {
                cursor?.let { put("cursor", it) }
                isNextPage?.let { put("isNextPage", it) }
                put("getPast", getPast)
                put("pageSize", pageSize)
            }

            val remoteResponse = withContext(Dispatchers.IO) {
                remoteApiService.getReservationPage(queryParams)
            }
            println("got online data")
            // Update local db with filtered data
            withContext(Dispatchers.IO) {
                reservationDao.insert(remoteResponse.data.map { it.asEntity() })
            }
            println("written to local db")

            emit(APIResource.Success(remoteResponse))

        } catch (e: Exception) {
            println("failed to fetch online reservations")
            if (localReservations.isEmpty()) {
                emit(APIResource.Error("Unable to fetch reservations: ${e.message}"))
            }
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
}