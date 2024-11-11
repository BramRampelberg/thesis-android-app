package com.example.android_2425_gent2.data.repository.reservation

import com.example.android_2425_gent2.data.local.dao.OfflineReservationDao
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import com.example.android_2425_gent2.data.network.reservation.ReservationApiService
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.asAPIResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class OfflineFirstReservationRepository(
    private val reservationDao: OfflineReservationDao,
    private val remoteApiService: ReservationApiService
) :
    ReservationRepository {

    override fun getReservations(
        cursor: Int?,
        isNextPage: Boolean?,
        getPast: Boolean,
        pageSize: Int
    ): Flow<APIResource<ReservationResponse>> = flow {
        emit(APIResource.Loading())

        try {
            val queryParams = buildMap<String, Any> {
                cursor?.let { put("cursor", it) }
                isNextPage?.let { put("isNextPage", it) }
                put("getPast", getPast)
                put("pageSize", pageSize)
            }

            println("query params: $queryParams")
            println("here")

            val response = remoteApiService.getReservationPage(queryParams)
            println("here2")

            emit(APIResource.Success(response))

        } catch (e: Exception) {

            println("no internet")
            /*
            val localReservations = reservationDao.getOfflineReservations()

            if (localReservations.count() < 1) {
                emit(APIResource.Error("No data available offline"))
            } else {
                val reservations = localReservations.map { it.asExternalModel() }

                val response = ReservationResponse(
                    data = reservations.toList(),
                    isOffline = true,
                    isFirstPage = true,
                    previousId = null,
                    nextId = null
                    )
                println("all offline reservations")
                println(response.data)
                println(response.data.count())
                emit(APIResource.Success(response))
            }

             */
        }
    }

    override suspend fun insertReservation(
        createRemoteReservationRequest: CreateRemoteReservationRequest
    ): Flow<APIResource<Int>>
    {
        return flow {
            emit(remoteApiService.createReservation(createRemoteReservationRequest))
        }.asAPIResource()

    }

}