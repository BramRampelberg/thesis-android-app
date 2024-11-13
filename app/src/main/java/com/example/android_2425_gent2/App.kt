package com.example.android_2425_gent2;

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.screens.MainScreen
import com.example.android_2425_gent2.ui.screens.login_page.LoginPage


@Composable
fun App(modifier: Modifier = Modifier) {
    val appState = rememberSaveable (stateSaver = AppStateSaver) {
        mutableStateOf(AppState())
    }

    if(!appState.value.isLoggedIn){
        LoginPage(login = { credentials ->
                appState.value = appState.value.copy(isLoggedIn = true)
                Log.i("LOGIN", "App state login with token: " + credentials.accessToken)
        }, modifier)
    }
    else{
        MainScreen(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}
