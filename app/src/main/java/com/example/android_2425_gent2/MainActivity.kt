package com.example.android_2425_gent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.ui.screens.MainScreen
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme

//import com.example.android_2425_gent2.ui.screens.MainScreen

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

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}

