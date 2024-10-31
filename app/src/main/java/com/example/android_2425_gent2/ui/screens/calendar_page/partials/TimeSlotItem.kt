package com.example.android_2425_gent2.ui.screens.calendar_page.partials

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun TimeSlotItem(
    slot: TimeSlot,
    heightDp: Float,
    onClick: () -> Unit

) {
    val startTime = LocalTime.parse(slot.start, DateTimeFormatter.ISO_TIME)
    val endTime = LocalTime.parse(slot.end, DateTimeFormatter.ISO_TIME)

    val backgroundColor = when {
        startTime.hour in 9..12 -> Color(0xFF42C4BE)
        startTime.hour in 14..17 -> Color(0xFFCCCCCC)
        else -> Color(0xFF4C5270)
    }
    val textColor = if (backgroundColor == Color(0xFF42C4BE)) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp.dp)
            .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 2.dp)
            .background(backgroundColor)
            .border(width = 1.dp, color = Color.Black)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Start: ${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = "End: ${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}
