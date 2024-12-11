package com.example.android_2425_gent2.data.repository.reservation

import com.example.android_2425_gent2.data.model.OfflineReservation
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime

class TestReservationRepository : ReservationRepository {

    override suspend fun getReservations(
        getPast: Boolean,
    ): Flow<APIResource<List<OfflineReservation>>> = flow {

        val mockReservations = listOf(
            OfflineReservation(
                start = LocalTime.of(9, 0),
                end = LocalTime.of(11, 0),
                date = LocalDate.now(),
                boatId = 1,
                boatPersonalName = "Speedboat 1",
                id = 1,
                isDeleted = false

            ),
            OfflineReservation(
                start = LocalTime.of(11, 30),
                end = LocalTime.of(13, 30),
                date = LocalDate.now(),
                boatId = 2,
                boatPersonalName = "Kayak 1",
                id = 2,
                isDeleted = false
            ),
            OfflineReservation(
                start = LocalTime.of(14, 0),
                end = LocalTime.of(16, 0),
                date = LocalDate.now(),
                boatId = 3,
                boatPersonalName = "Canoe 1",
                id = 3,
                isDeleted = false
            )
        )




        emit(APIResource.Loading(null))


        delay(100)


        emit(APIResource.Success(mockReservations))
    }

    override suspend fun insertReservation(createRemoteReservationRequest: CreateRemoteReservationRequest): Flow<APIResource<Int>> {
        delay(2000)
        return listOf(APIResource.Success(1)).asFlow()
    }

    override suspend fun cancelReservation(reservationId: Int): Flow<APIResource<Unit>> {
        TODO("Not yet implemented")
    }


}