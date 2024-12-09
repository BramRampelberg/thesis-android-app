package com.example.android_2425_gent2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.navigation.BottomNavigationBar
import com.example.android_2425_gent2.ui.navigation.NavigationHost

@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    logout: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.testTag("MainScreen"),
        bottomBar = {
        BottomNavigationBar(
            modifier = modifier,
            navController = navController
        )
    }) { innerPadding ->
        NavigationHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            logout = logout
        )
    }
}


