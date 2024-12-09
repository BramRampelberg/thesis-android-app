package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto

@Composable
fun ReservationDetailsPersonInfo(
    details: ReservationDetailsDto,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Naam ophaal persoon", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (details.currentBatteryUserName.isNullOrBlank() || 
            details.currentHolderPhoneNumber.isNullOrBlank() || 
            details.currentHolderEmail.isNullOrBlank()) {
            Text("No pickup information available")
        } else {
            Text(details.currentBatteryUserName)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Contact gegevens", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(details.currentHolderPhoneNumber)
            Text(details.currentHolderEmail)
        }
    }
}