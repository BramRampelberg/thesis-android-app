package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.network.model.UserSurfaceInfoDto
import com.example.android_2425_gent2.data.network.users.UserApiService
import kotlinx.coroutines.flow.Flow
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RemoteUserRepository (
    private val remoteUserRepository: UserApiService

) : UserRepository {

    override suspend fun getReservations(): Flow<APIResource<List<UserSurfaceInfoDto>>> = flow {

        emit(APIResource.Loading())

        val result = withContext(Dispatchers.IO) {
            try {
                val response = remoteUserRepository.getGuests()
                APIResource.Success(response)
            } catch (e: Exception) {
                APIResource.Error("Failed to fetch user surface info: ${e.message}")
            }
        }

        // Emit the final result
        emit(result)
    }.flowOn(Dispatchers.IO)
}