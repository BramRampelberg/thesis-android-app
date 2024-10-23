package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.local.AppDatabase
import com.example.android_2425_gent2.data.model.Reservation

@Composable
fun ReservationList(
    reservations: List<Reservation>,
    onSelectedReservationChange: (Reservation) -> Unit,
    modifier: Modifier
) {
    val db = AppDatabase.getDatabase(LocalContext.current)
    db.userDao()
//    userDao.insertAll(UserEntity(1))

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(
            reservations
            //getMockReservations()
        ) { item ->
            ReservationCard(
                item,
                onSelectedReservationChange,
                modifier = modifier
            )
        }
    }
}