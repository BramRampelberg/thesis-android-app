package com.example.android_2425_gent2.ui.screens.login_page

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    var credentialsState by mutableStateOf(CredentialsState("", ""))
        private set

    var uiState by mutableStateOf(UiState())
        private set

    fun setEmail(email: String) {
        credentialsState = CredentialsState(email, credentialsState.password)
    }

    fun setPassword(password: String) {
        credentialsState = CredentialsState(credentialsState.email, password)
    }

    fun setLoading(isLoading: Boolean) {
        uiState = UiState(isLoading, uiState.error)
    }

    fun handleLogin() {
        setLoading(true)
        viewModelScope.launch {
            delay(3000) // 3 seconds delay
            setLoading(false)
        }
    }
}

data class CredentialsState(
    val email: String,
    val password: String
)

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null
)