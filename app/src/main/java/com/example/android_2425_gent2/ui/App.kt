package com.example.android_2425_gent2.ui;

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.screens.MainScreen
import com.example.android_2425_gent2.ui.screens.login_page.LoginPage


@Composable
fun App(modifier: Modifier = Modifier) {
    val viewModel: AppViewModel = viewModel(
        factory = AppViewModelProvider.Factory,
    )

    val appState = viewModel.appState

    if(!appState.isLoggedIn){
        LoginPage(login = { credentials ->
                viewModel.login()
                Log.i("LOGIN", "App state login with token: " + credentials.accessToken)
        }, modifier = modifier)
    }
    else{
        MainScreen(
            navController = rememberNavController(),
            modifier = modifier
        )
    }
}
