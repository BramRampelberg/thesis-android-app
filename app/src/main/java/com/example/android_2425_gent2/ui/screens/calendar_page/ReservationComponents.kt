package com.example.android_2425_gent2.ui.screens.calendar_page

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.network.RetrofitClient
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

val PrimaryBlue = Color(0xFF42C4BE)
val LightGray = Color(0xFFCCCCCC)
val Darkblue = Color(0xFF4C5270)

@Composable
fun CalendarView(onDateSelected: (LocalDate) -> Unit, selectedDate: LocalDate?) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    // State to hold the API response data
    val timeSlotState = remember { mutableStateOf<List<DayInfo>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf("") }

    // Fetch data from API for the current month
    LaunchedEffect(currentMonth) {
        val startDate = currentMonth.atDay(1).toString()  // Convert LocalDate to String for API
        val endDate = currentMonth.atEndOfMonth().toString()

        isLoading.value = true
        try {
            val response = RetrofitClient.apiService.getTimeSlots(startDate, endDate)
            timeSlotState.value = response.days
            isLoading.value = false
        } catch (e: Exception) {
            errorMessage.value = "Error: ${e.message}"
            isLoading.value = false
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        MonthSelector(currentMonth) { newMonth ->
            currentMonth = newMonth
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading.value) {
            Text("Loading...")
        } else if (errorMessage.value.isNotEmpty()) {
            Text(errorMessage.value)
        } else {
            MonthCalendar(
                currentMonth = currentMonth,
                onDateSelected = onDateSelected,
                selectedDate = selectedDate,
                availableDays = timeSlotState.value ?: emptyList() // Pass the fetched days
            )
        }

        selectedDate?.let {
            Spacer(modifier = Modifier.height(16.dp))
            TimeSlotView(it) // Pass the selected date to show available time slots
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
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous month")
        }
        Text(
            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next month")
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
    currentMonth: YearMonth,
    onDateSelected: (LocalDate) -> Unit,
    selectedDate: LocalDate?,
    availableDays: List<DayInfo> // Pass the available days from API
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7
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
                val date = currentMonth.atDay(day + 1)

                // Find if this day is available from the API response
                val dayInfo = availableDays.find { LocalDate.parse(it.date) == date }

                DayCell(
                    date = date,
                    isSelected = date == selectedDate,
                    isToday = date == today,
                    isAvailable = dayInfo?.isSlotAvailable == true, // Check availability
                    isFullyBooked = dayInfo?.isFullyBooked == true, // Check if fully booked
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
    isAvailable: Boolean,  // From API response
    isFullyBooked: Boolean, // From API response
    onDateSelected: (LocalDate) -> Unit
) {
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
            .clickable(enabled = isAvailable && !isFullyBooked) { onDateSelected(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = when {
                isSelected -> Color.White
                !isAvailable || isFullyBooked -> Color.LightGray
                else -> Color.Black
            },
            fontWeight = if (isAvailable && !isFullyBooked) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}




@Composable
fun TimeSlotView(selectedDate: LocalDate) {
    val timeSlotState = remember { mutableStateOf<List<TimeSlot>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf("") }

    val year = selectedDate.year
    val month = selectedDate.monthValue
    val day = selectedDate.dayOfMonth

    val startTime = LocalTime.of(9, 0)
    val endTime = LocalTime.of(21, 0)

    LaunchedEffect(selectedDate) {
        isLoading.value = true
        try {
            val response = RetrofitClient.apiService.getTimeSlotsForDay(year, month, day)
            timeSlotState.value = response
            isLoading.value = false
        } catch (e: Exception) {
            errorMessage.value = "Error: ${e.message}"
            isLoading.value = false
        }
    }

    if (isLoading.value) {
        Text("Loading time slots...")
    } else if (errorMessage.value.isNotEmpty()) {
        Text(errorMessage.value)
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Timeline column
                        Box(modifier = Modifier.width(50.dp)) {
                            Column {
                                for (hour in startTime.hour..endTime.hour) {
                                    Text(
                                        text = String.format("%02d:00", hour),
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .height(60.dp)
                                            .padding(top = 8.dp)
                                    )
                                }
                            }
                        }

                        // Time slots column
                        Box(modifier = Modifier.weight(1f)) {
                            // Calculate absolute positions for all slots
                            timeSlotState.value?.forEach { slot ->
                                val slotStartTime = LocalTime.parse(slot.start)
                                val slotEndTime = LocalTime.parse(slot.end)

                                // Calculate offset from the start of the day
                                val startOffsetMinutes = (slotStartTime.hour - startTime.hour) * 60 + slotStartTime.minute
                                val durationMinutes = Duration.between(slotStartTime, slotEndTime).toMinutes()

                                Box(
                                    modifier = Modifier
                                        .offset(y = ((startOffsetMinutes / 60f) * 60).dp)
                                        .fillMaxWidth()
                                ) {
                                    TimeSlotItem(
                                        slot = slot,
                                        heightDp = (durationMinutes / 60f) * 60
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSlotItem(
    slot: TimeSlot,
    heightDp: Float
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
            .clickable { /* Handle click */ }
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








