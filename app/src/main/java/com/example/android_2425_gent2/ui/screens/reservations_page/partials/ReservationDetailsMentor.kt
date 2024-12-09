package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ReservationDetailsMentor(
    mentorName: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Meter/Peter", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (mentorName != null) {
            Text(mentorName)
        } else {
            Text("No mentor assigned")
        }
    }
}