package com.example.android_2425_gent2.ui.screens.calendar_page


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.network.RetrofitClient
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.calendar_page.partials.MonthCalendar
import com.example.android_2425_gent2.ui.screens.calendar_page.partials.MonthSelector
import com.example.android_2425_gent2.ui.screens.calendar_page.partials.TimeSlotDetailsBottomSheet
import java.time.LocalDate
import java.time.YearMonth
import com.example.android_2425_gent2.ui.screens.calendar_page.partials.TimeSlotView

val PrimaryBlue = Color(0xFF42C4BE)
val LightGray = Color(0xFFCCCCCC)
val Darkblue = Color(0xFF4C5270)

@Composable
fun CalendarView(
    viewModel: CalendarViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        MonthSelector(
            currentMonth = uiState.currentMonth,
            onMonthChange = { viewModel.changeMonth(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CalendarContent(
            uiState = uiState,
            onDateSelected = { viewModel.selectDate(it) }
        )

        if (uiState.selectedDate != null) {
            Spacer(modifier = Modifier.height(16.dp))
            TimeSlotContent(
                uiState = uiState,
                onTimeSlotClick = { viewModel.onTimeSlotSelected(it) }
            )
        }
    }

    uiState.selectedTimeSlot?.let { timeSlot ->
        TimeSlotDetailsBottomSheet(
            timeSlot = timeSlot,
            onDismiss = { viewModel.onTimeSlotDismissed() }
        )
    }

}



@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    onDateSelected: (LocalDate) -> Unit
) {
    when {
        uiState.isLoadingMonth -> LoadingIndicator()
        uiState.monthErrorMessage.isNotEmpty() -> ErrorMessage(uiState.monthErrorMessage)
        else -> MonthCalendar(
            currentMonth = uiState.currentMonth,
            selectedDate = uiState.selectedDate,
            onDateSelected = onDateSelected,
            availableDays = uiState.monthTimeSlots
        )
    }
}


@Composable
private fun TimeSlotContent(uiState: CalendarUiState,  onTimeSlotClick: (TimeSlot) -> Unit) {
    when {
        uiState.isLoadingDaily -> LoadingIndicator()
        uiState.dailyErrorMessage.isNotEmpty() -> ErrorMessage(uiState.dailyErrorMessage)
        else ->  TimeSlotView(
            timeSlots = uiState.dailyTimeSlots,
            onTimeSlotClick = onTimeSlotClick

        )
    }
}


@Composable
private fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp)
    )
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error
    )
}

