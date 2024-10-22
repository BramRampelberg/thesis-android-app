package com.example.android_2425_gent2

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlotResponse

//import com.example.android_2425_gent2.ui.screens.MainScreen
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import com.example.android_2425_gent2.network.RetrofitClient

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
    // State to hold the API response data
    val timeSlotState = remember { mutableStateOf<TimeSlotResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf("") }

    // Stel de start- en einddatum in (dit kan dynamisch zijn)
    val startDate = "2024-10-01"
    val endDate = "2024-10-31"

    LaunchedEffect(Unit) {
        isLoading.value = true
        try {
            // Roep de API aan met de queryparameters
            val response = RetrofitClient.apiService.getTimeSlots(startDate, endDate)
            timeSlotState.value = response
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
        timeSlotState.value?.let { timeSlotResponse ->
            LazyColumn {
                item {
                    // Display the start, end, and totalDays
                    Text("Start: ${timeSlotResponse.start}")
                    Text("End: ${timeSlotResponse.end}")
                    Text("Total Days: ${timeSlotResponse.totalDays}")
                }
                // Iterate over the days and display the information
                items(timeSlotResponse.days) { day ->
                    Text("Date: ${day.date}, Fully Booked: ${day.isFullyBooked}, Available: ${day.isSlotAvailable}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}


