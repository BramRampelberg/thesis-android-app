package com.example.android_2425_gent2.ui.screens.login_page

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    var credentialsState by mutableStateOf(CredentialsState("", ""))
        private set

    fun setUsername(username: String) {
        credentialsState = CredentialsState(username, credentialsState.password)
    }

    fun setPassword(password: String) {
        credentialsState = CredentialsState(credentialsState.username, password)
    }

    fun logCredentials() {
        Log.d("Login", credentialsState.username)
        Log.d("Login", credentialsState.password)
    }
}

data class CredentialsState(
    val username: String,
    val password: String
)