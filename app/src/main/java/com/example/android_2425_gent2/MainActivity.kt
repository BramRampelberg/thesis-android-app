package com.example.android_2425_gent2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_2425_gent2.data.remote.model.RemoteReservation
import com.example.android_2425_gent2.network.RetrofitClient

//import com.example.android_2425_gent2.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
            Android2425gent2Theme {
                MainScreen(
                    navController = rememberNavController(),
                    modifier = Modifier
                )
            }
             */
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val reservationsState = remember { mutableStateOf(emptyList<RemoteReservation>()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        isLoading.value = true
        try {
            reservationsState.value = RetrofitClient.apiService.getReservations()
            isLoading.value = false
        } catch (e: Exception) {
            errorMessage.value = "Error: ${e.message}"
            isLoading.value = false
        }
    }
    
    if (isLoading.value) {
        Text("Loading...")
    } else if (errorMessage.value.isNotEmpty()) {
        Text(errorMessage.value)
    } else {
        
        LazyColumn {
            items(reservationsState.value) { reservation ->
                Text("Reservation ID: ${reservation.id}, Start: ${reservation.start}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}

