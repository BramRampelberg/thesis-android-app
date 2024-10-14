package com.example.android_2425_gent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.screens.MainScreen
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android2425gent2Theme {
                MainScreen(
                    navController = rememberNavController(),
                    modifier = Modifier
                )
            }
        }
    }
}