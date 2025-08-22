package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.model.Reservation

@Composable
fun ReservationList(
    reservations: List<Reservation>,
    onSelectedReservationChange: (Reservation) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = reservations,
            key = { it.id }
        ) { reservation ->
            Card(
                onClick = { onSelectedReservationChange(reservation) },
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = modifier.padding(12.dp)) {
                    ImportantReservationInfo(reservation = reservation, modifier = modifier)
                }
            }
        }
    }
}