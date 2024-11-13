package com.example.android_2425_gent2.data.repository.reservation

import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TestReservationRepository : ReservationRepository {
    /*override fun getReservationsByUserStream(userId: Int): Flow<List<Reservation>> =
        listOf(listOf<Reservation>()).asFlow()

    override fun getAllReservationsStream(): Flow<List<Reservation>> =
        listOf(getTestReservations()).asFlow()

    override fun getAllUpcomingReservationsStream(): Flow<List<Reservation>> =
        listOf(listOf(getTestReservations()[2])).asFlow()

    override fun getAllPastReservationsStream(): Flow<List<Reservation>> =
        listOf(listOf(getTestReservations()[1])).asFlow()

    override fun getReservationStream(id: Int): Flow<Reservation?> =
        listOf(getTestReservations().filter { it.id == id }.first()).asFlow()
*/
    override suspend fun getReservations(
        cursor: Int?,
        isNextPage: Boolean?,
        getPast: Boolean,
        pageSize: Int
    ): Flow<APIResource<ReservationResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertReservation(createRemoteReservationRequest: CreateRemoteReservationRequest): Flow<APIResource<Int>> {
        delay(2000)
        return listOf(APIResource.Success(1)).asFlow()
    }

     suspend fun insertReservation(reservation: Reservation) {

    }
/*
    override suspend fun deleteReservation(reservation: Reservation) {

    }

    override suspend fun updateReservation(reservation: Reservation) {

    }

 */
}