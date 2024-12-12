package com.example.android_2425_gent2.data.repository.boat

import com.example.android_2425_gent2.data.network.boat.BoatDto
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestBoatRepository : BoatRepository {
    override suspend fun getBoats(): Flow<APIResource<List<BoatDto>>> = flow {
        emit(APIResource.Success(listOf(
            BoatDto(1, "Test Boat 1", "Limba", true),
            BoatDto(2, "Test Boat 2", "Leith", true),
            BoatDto(3, "Test Boat 3", "Lubeck", true)
        )))
    }
} 