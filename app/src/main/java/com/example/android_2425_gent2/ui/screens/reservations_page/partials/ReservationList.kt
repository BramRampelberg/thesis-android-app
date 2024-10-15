package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.local.entity.UserEntity
import com.example.android_2425_gent2.data.local.getDb
import com.example.android_2425_gent2.data.model.Battery
import com.example.android_2425_gent2.data.model.Boat
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.model.TimeSlot
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationList(onSelectedReservationChange: (Reservation) -> Unit, modifier: Modifier) {
    val db = getDb(LocalContext.current)
    val userDao = db.userDao()
    userDao.insertAll(UserEntity(1))
    
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(userDao.getUserWithReservations(1).reservations.map {
            Reservation(
                it.id,
                Boat(it.boatId, ""),
                Battery(1),
                TimeSlot(1, LocalDate.now(), LocalTime.now(), LocalTime.now())
            )
        }.toList()) { item ->
            ReservationCard(
                item,
                onSelectedReservationChange,
                modifier = modifier
            )
        }
    }
}