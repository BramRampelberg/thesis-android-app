package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ReservationDetailsAddress(
    details: ReservationDetailsDto,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Adres", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (details.currentHolderStreet.isNullOrBlank() || 
            details.currentHolderNumber.isNullOrBlank() || 
            details.currentHolderCity.isNullOrBlank() || 
            details.currentHolderPostalCode.isNullOrBlank()) {
            Text("No pickup information available")
        } else {
            Text("${details.currentHolderStreet} ${details.currentHolderNumber}")
            Text("${details.currentHolderPostalCode} ${details.currentHolderCity}")
        }
    }
}