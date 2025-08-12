package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.network.model.UserDetailsDto
import com.example.android_2425_gent2.data.network.users.UserApiService
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteUserRepository(
    private val userService: UserApiService

) : UserRepository {
    override suspend fun getUserDetails(): Flow<APIResource<UserDetailsDto>> = flow {
        try {
            emit(APIResource.Loading())
            val response = userService.getUserDetails()
            emit(APIResource.Success(response))
        } catch (e: Exception) {
            emit(APIResource.Error(message = "An unexpected error occurred"))
        }
    }
}