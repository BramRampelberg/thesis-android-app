package com.example.android_2425_gent2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.navigation.BottomNavigationBar
import com.example.android_2425_gent2.ui.navigation.NavigationHost

@Preview
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    Scaffold(bottomBar = {
        BottomNavigationBar(
            navController = navController,
            modifier = modifier
        )
    }) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = modifier.padding(innerPadding)
        )
    }
}


