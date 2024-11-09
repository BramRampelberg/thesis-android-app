package com.example.android_2425_gent2.ui.screens.calendar_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.network.model.CreateRemoteReservationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.ReservationRepository
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.time.YearMonth
import java.time.LocalDate


enum class ReservationState {
    DETAILS,
    PAYMENT_LOADING,
    ERROR,
    CONFIRMATION
}

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
    val showReservationFlow: Boolean = false,
    val reservationState: ReservationState = ReservationState.DETAILS,
    val reservationErrorMessage: String = ""
    )


class CalendarViewModel(
    private val timeSlotRepository: TimeSlotRepository,
   private val reservationRepository: ReservationRepository
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
                val response = timeSlotRepository.getTimeSlotsForDay(
                    date.year,
                    date.monthValue,
                    date.dayOfMonth
                )
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
        val timeSlotId = _uiState.value.selectedTimeSlot?.id ?: return

        _uiState.update {
            it.copy(
                showReservationFlow = true,
                reservationState = ReservationState.PAYMENT_LOADING,
                reservationErrorMessage = "" // Clear any previous error
            )
        }

        viewModelScope.launch {
            delay(2000) // Simulate payment

            reservationRepository.insertReservation(CreateRemoteReservationRequest(timeSlotId))
                .collect { result ->
                    when (result) {
                        is APIResource.Loading -> {
                            println("making a reservation")
                        }
                        is APIResource.Success -> {
                            println("has been made")
                            _uiState.update { currentState ->
                                currentState.copy(
                                    reservationState = ReservationState.CONFIRMATION,
                                    reservationErrorMessage = "",

                                )
                            }
                            refreshTimeSlots()

                        }
                        is APIResource.Error -> {
                            val errorMessage = result.message ?: "Unknown error occurred"
                            println(errorMessage)
                            _uiState.update { currentState ->
                                currentState.copy(
                                    reservationState = ReservationState.ERROR,
                                    reservationErrorMessage = errorMessage
                                )

                            }

                        }
                    }
                }
        }
    }

    private fun refreshTimeSlots() {

        fetchTimeSlotsForMonth(_uiState.value.currentMonth)

        _uiState.value.selectedDate?.let { date ->
            fetchTimeSlotsForDay(date)
        }
    }

    fun clearReservationError() {
        _uiState.update { it.copy(reservationErrorMessage = "") }
    }
        fun onReservationConfirmed() {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedTimeSlot = null,
                    showReservationFlow = false,
                    reservationState = ReservationState.DETAILS
                )
            }
        }
}






