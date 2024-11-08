package com.example.android_2425_gent2.ui.screens.login_page

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
        credentialsState = credentialsState.copy(
            email = email,
            emailTouched = true
        )
    }

    fun setPassword(password: String) {
        credentialsState = credentialsState.copy(
            password = password,
            passwordTouched = true
        )
    }

    fun onAnyInputChanged() {
        setDisableLogin(!validateCredentials())
    }

    private fun setLoading(isLoading: Boolean) {
        uiState = uiState.copy(
            isLoading = isLoading
        )
    }

    private fun setError(error: String?) {
        uiState = uiState.copy(
            error = error
        )
    }

    private fun setDisableLogin(disable: Boolean) {
        uiState = UiState(uiState.isLoading, uiState.error, disable)
    }

    private fun validateCredentials(): Boolean {

        return when {
            credentialsState.emailTouched && credentialsState.email.isEmpty() -> {
                setError("Email is required")
                false
            }
            credentialsState.passwordTouched && credentialsState.password.isEmpty() -> {
                setError("Password is required")
                false
            }
            credentialsState.passwordTouched && credentialsState.password.length < 8 -> {
                setError("Password must be at least 8 characters")
                false
            }
            credentialsState.passwordTouched && credentialsState.password.length > 72 -> {
                setError("Password must be less than 72 characters")
                false
            }
            else -> {
                setError(null)
                // Only enable login if both fields have content
                credentialsState.email.isNotEmpty() && credentialsState.password.isNotEmpty()
            }
        }
    }

    fun handleLogin() {
        setLoading(true)
        if(uiState.error == null) {
            setError("Incorrect password")
        } else {
            setError(null)
        }
        viewModelScope.launch {
            delay(3000)
            setLoading(false)
        }
    }
}

data class CredentialsState(
    val email: String,
    val password: String,
    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false
)

data class UiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val disableLogin: Boolean = true
)