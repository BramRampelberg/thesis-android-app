package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsBoatFilled
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.utils.DATE_FORMATTER
import com.example.android_2425_gent2.utils.TIME_FORMATTER
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportantReservationInfo(
    reservation: Reservation,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.CalendarMonth,
            contentDescription = "Calendar Icon",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "${
                stringResource(R.string.date).replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }: ${reservation.date.format(DATE_FORMATTER)}",
            fontSize = 20.sp, modifier = modifier,
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.AccessTime,
            contentDescription = "Clock Icon",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "${reservation.start.format(TIME_FORMATTER)} - ${reservation.end.format(TIME_FORMATTER)}",
            fontSize = 32.sp,
            modifier = modifier
        )
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Filled.DirectionsBoatFilled,
            contentDescription = "Boat Icon",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "${
                stringResource(R.string.boat).replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }: ${reservation.boatPersonalName}",
            fontSize = 20.sp, modifier = modifier,
        )
    }
}

