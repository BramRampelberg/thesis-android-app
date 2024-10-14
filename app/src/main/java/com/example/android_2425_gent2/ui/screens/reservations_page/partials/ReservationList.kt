package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.repository.getMockReservations

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationList(onSelectedReservationChange: (Reservation) -> Unit, modifier: Modifier) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(getMockReservations()) { item ->
            ReservationCard(
                item,
                onSelectedReservationChange,
                modifier = modifier
            )
        }
    }
}