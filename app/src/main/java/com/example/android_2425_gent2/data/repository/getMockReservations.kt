package com.example.android_2425_gent2.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.android_2425_gent2.data.model.Battery
import com.example.android_2425_gent2.data.model.Boat
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.model.TimeSlot
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun getMockReservations(): List<Reservation> {
    return (1..20).map {
        Reservation(
            id = it,
            boat = Boat(
                id = it,
                name = "Boat $it"
            ),
            battery = Battery(
                id = it
            ),
            timeSlot = TimeSlot(
                id = it,
                date = LocalDate.of(2024, 10, (it % 3) + 1),
                start = LocalTime.of(9 + (it * 3) % 13, 0),
                end = LocalTime.of(9 + (it * 3) % 13, 0).plusHours(3)
            )
        )
    }.toList<Reservation>()

}