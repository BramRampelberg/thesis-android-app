package com.example.android_2425_gent2.ui.screens.calendar_page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.mock_data.ReservationMock
import com.example.android_2425_gent2.data.mock_data.TimeSlot
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val PrimaryBlue = Color(0xFF42C4BE)
val LightGray = Color(0xFFCCCCCC)
val Darkblue = Color(0xFF4C5270)

@Composable
fun CalendarView(onDateSelected: (LocalDate) -> Unit, selectedDate: LocalDate?) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(modifier = Modifier.padding(16.dp)) {
        MonthSelector(currentMonth) { newMonth ->
            currentMonth = newMonth
        }
        Spacer(modifier = Modifier.height(16.dp))
        MonthCalendar(currentMonth, onDateSelected, selectedDate)
        selectedDate?.let {
            Spacer(modifier = Modifier.height(16.dp))
            TimeSlotView(it)
        }
    }
}

@Composable
fun MonthSelector(currentMonth: YearMonth, onMonthChange: (YearMonth) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Previous month")
        }
        Text(
            text = "${
                currentMonth.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                )
            } ${currentMonth.year}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Next month")
        }
    }
}

@Composable
fun WeekdayHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        listOf("Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo").forEach { day ->
            Text(
                text = day,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MonthCalendar(
    yearMonth: YearMonth,
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate?
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7
    val today = LocalDate.now()

    Column {
        WeekdayHeader()
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(300.dp)
        ) {
            items(firstDayOfWeek) {
                Box(modifier = Modifier.aspectRatio(1f))
            }
            items(daysInMonth) { day ->
                val date = yearMonth.atDay(day + 1)
                DayCell(
                    date = date,
                    isSelected = date == selectedDate,
                    isToday = date == today,
                    onDateSelected = onDateSelected
                )
            }
        }
    }
}


@Composable
fun DayCell(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val dayReservation = remember(date) { ReservationMock.getReservationsForDate(date) }
    val isAvailable = date >= LocalDate.now() && dayReservation?.isFullyBooked == false
    val hasYourReservation = dayReservation?.hasYourReservation == true

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> PrimaryBlue
                    else -> Color.Transparent
                }
            )
            .border(1.dp, if (isSelected) PrimaryBlue else Color.Transparent, CircleShape)
            .clickable(enabled = isAvailable || hasYourReservation) { onDateSelected(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = when {
                isSelected -> Color.White
                !isAvailable -> Color.LightGray
                else -> Color.Black
            },
            fontWeight = if (isAvailable || hasYourReservation) FontWeight.SemiBold else FontWeight.Normal
        )
        if (dayReservation != null) {
            if (hasYourReservation && !dayReservation.isFullyBooked) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                )
            }
        }
    }
}


@Composable
fun TimeSlotView(selectedDate: LocalDate) {
    val startTime = LocalTime.of(9, 0)
    val endTime = LocalTime.of(20, 0)
    val dayReservation =
        remember(selectedDate) { ReservationMock.getReservationsForDate(selectedDate) }
    val timeSlots = dayReservation?.timeSlots ?: emptyList()

    Column {
        Text(
            "Dag overzicht",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Tijdlijn kolom
                    Column(modifier = Modifier.width(50.dp)) {
                        var currentTime = startTime
                        while (currentTime <= endTime) {

                            Text(
                                text = currentTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                color = Color.Gray,
                                modifier = Modifier
                                    .height(60.dp)
                                    .padding(top = 8.dp)
                            )
                            currentTime = currentTime.plusHours(1)
                        }
                    }


                    Column(modifier = Modifier.weight(1f)) {
                        timeSlots.forEach { slot ->
                            TimeSlotItem(slot)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSlotItem(slot: TimeSlot) {

    val backgroundColor = when {
        slot.isYourReservation -> PrimaryBlue
        !slot.isAvailable -> LightGray
        else -> Color.White
    }


    val textColor = if (slot.isYourReservation) Color.White else Color.Black


    val durationInHours = slot.endTime.hour - slot.startTime.hour


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp * durationInHours)
            .padding(start = 8.dp, top = 2.dp, bottom = 2.dp, end = 2.dp)
            .background(backgroundColor)
            .border(width = 1.dp, color = Color.Black)
            .clickable(enabled = slot.isAvailable || slot.isYourReservation) { /* Handle click */ }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Toon de status van het tijdslot (Uw reservatie, Beschikbaar, Volzet)
            Text(
                text = when {
                    slot.isYourReservation -> "Uw reservatie"
                    slot.isAvailable -> "Beschikbaar"
                    else -> "Volzet"
                },
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )


            Text(
                text = "${slot.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${
                    slot.endTime.format(
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                }",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}




