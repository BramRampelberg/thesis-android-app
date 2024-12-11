package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.model.UserRole
import com.example.android_2425_gent2.data.repository.auth.IAuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfilePageViewModel(
    private val authRepo: IAuthRepo
): ViewModel() {
    private val _isAdmin = MutableStateFlow(false)

    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()
    fun handleLogout() {
        authRepo.logout()
    }


    init {
        viewModelScope.launch {
            _isAdmin.value = authRepo.hasRole(UserRole.Administrator)
        }
    }


}