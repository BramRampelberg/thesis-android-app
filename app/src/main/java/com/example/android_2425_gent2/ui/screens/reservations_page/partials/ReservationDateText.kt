package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.model.Reservation

@Composable
fun ReservationDateText(reservation: Reservation, modifier: Modifier) {
    Text(reservation.timeSlot.date.toString(), fontSize = 20.sp)
}