package com.example.android_2425_gent2.data.repository.reservation

import com.example.android_2425_gent2.data.model.OfflineReservation
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {

    suspend fun getReservations(
        cursor: Int? = null,
        isNextPage: Boolean? = true,
        getPast: Boolean = false,
        pageSize: Int = 5
    ): Flow<APIResource<List<OfflineReservation>>>


    suspend fun insertReservation(createRemoteReservationRequest: CreateRemoteReservationRequest): Flow<APIResource<Int>>

}