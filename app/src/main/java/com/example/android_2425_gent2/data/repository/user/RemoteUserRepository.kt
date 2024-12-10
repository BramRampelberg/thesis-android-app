package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.model.UserSurface
import com.example.android_2425_gent2.data.model.toDomain
import com.example.android_2425_gent2.data.network.users.UserApiService
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class RemoteUserRepository (
    private val remoteUserRepository: UserApiService

) : UserRepository {

    override suspend fun getUsers(): Flow<APIResource<List<UserSurface>>> = flow {

        emit(APIResource.Loading())

        val result = withContext(Dispatchers.IO) {
            try {
                println("trying to get users")
                val response = remoteUserRepository.getGuests().items.map { it.toDomain() }
                APIResource.Success(response)
            } catch (e: Exception) {
                APIResource.Error("Failed to fetch user surface info: ${e.message}")
            }
        }

        // Emit the final result
        emit(result)
    }.flowOn(Dispatchers.IO)
}