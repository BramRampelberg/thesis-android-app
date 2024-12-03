package com.example.android_2425_gent2.data.repository.user

import com.example.android_2425_gent2.data.model.UserSurface
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestUserRepository : UserRepository {
    private var _stateFlow = MutableStateFlow<APIResource<List<UserSurface>>>(APIResource.Loading())
    val stateFlow: StateFlow<APIResource<List<UserSurface>>> get() = _stateFlow

    override suspend fun getUsers(): Flow<APIResource<List<UserSurface>>> = stateFlow

    fun triggerLoading() {
        _stateFlow.value = APIResource.Loading()
    }

    fun triggerError(errorMessage: String) {
        _stateFlow.value = APIResource.Error(errorMessage)
    }


    fun triggerSuccessWithCustomUsers(users: List<UserSurface>) {
        _stateFlow.value = APIResource.Success(users)
    }

}