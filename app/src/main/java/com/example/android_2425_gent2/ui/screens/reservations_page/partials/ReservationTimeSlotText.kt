package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.utils.TIME_FORMATTER

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationTimeSlotText(reservation: Reservation, modifier: Modifier) {
    Text(
        "${reservation.timeSlot?.start?.format(TIME_FORMATTER)} - ${
            reservation.timeSlot?.end?.format(
                TIME_FORMATTER
            )
        }",
        fontSize = 32.sp
    )
}