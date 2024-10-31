package com.example.android_2425_gent2.ui.screens.calendar_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.network.RetrofitClient
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.YearMonth
import java.time.LocalDate



data class CalendarUiState(
    val currentMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate? = null,
    val monthTimeSlots: List<DayInfo> = emptyList(),
    val isLoadingMonth: Boolean = false,
    val monthErrorMessage: String = "",
    val dailyTimeSlots: List<TimeSlot> = emptyList(),
    val isLoadingDaily: Boolean = false,
    val dailyErrorMessage: String = "",
    val selectedTimeSlot: TimeSlot? = null,
    val showReservationConfirmation: Boolean = false,
)


class CalendarViewModel(
    private val timeSlotRepository: TimeSlotRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        fetchTimeSlotsForMonth(_uiState.value.currentMonth)
    }

    fun changeMonth(newMonth: YearMonth) {
        _uiState.update { it.copy(currentMonth = newMonth) }
        fetchTimeSlotsForMonth(newMonth)
    }

    fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        fetchTimeSlotsForDay(date)
    }

    private fun fetchTimeSlotsForMonth(month: YearMonth) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingMonth = true) }
            try {
                val startDate = month.atDay(1).toString()
                val endDate = month.atEndOfMonth().toString()
                val response = timeSlotRepository.getTimeSlotsForRange(startDate, endDate)
                _uiState.update {
                    it.copy(
                        monthTimeSlots = response?.days ?: emptyList(),
                        isLoadingMonth = false,
                        monthErrorMessage = ""
                    )
                }
            } catch (e: Exception) {
                println("Exception caught: ${e.message}")  // Debugging line
                _uiState.update {
                    it.copy(
                        monthTimeSlots = emptyList(),
                        isLoadingMonth = false,
                        monthErrorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }





    private fun fetchTimeSlotsForDay(date: LocalDate) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingDaily = true) }
            try {
                val response = timeSlotRepository.getTimeSlotsForDay(date.year, date.monthValue, date.dayOfMonth)
                _uiState.update {
                    it.copy(
                        dailyTimeSlots = response,
                        isLoadingDaily = false,
                        dailyErrorMessage = ""
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        dailyTimeSlots = emptyList(),
                        isLoadingDaily = false,
                        dailyErrorMessage = "Error: ${e.message}"
                    )
                }
            }
        }
    }



    //bottom model
    private val _selectedTimeSlot = MutableStateFlow<TimeSlot?>(null)
    val selectedTimeSlot: StateFlow<TimeSlot?> = _selectedTimeSlot.asStateFlow()


    fun onTimeSlotSelected(timeSlot: TimeSlot) {
        _uiState.update { currentState ->
            currentState.copy(selectedTimeSlot = timeSlot)
        }
    }

    fun onTimeSlotDismissed() {
        _uiState.update { currentState ->
            currentState.copy(selectedTimeSlot = null)
        }
    }


    fun onReserveClicked() {
        _uiState.update { currentState ->
            currentState.copy(showReservationConfirmation = true)
        }
    }

    fun onReservationConfirmed() {
        // Here you would typically make your API call to confirm the reservation
        // After successful confirmation:
        _uiState.update { currentState ->
            currentState.copy(
                selectedTimeSlot = null,
                showReservationConfirmation = false
            )
        }
    }
}



