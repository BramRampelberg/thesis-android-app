package com.example.android_2425_gent2.calendarTest

import com.example.android_2425_gent2.calendarTest.coroutine.MainDispatcherRule
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.data.remote.model.TimeSlotResponse
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.ui.screens.calendar_page.CalendarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModelTest {

    @get:Rule

    val coroutineRule = MainDispatcherRule()

    private lateinit var viewModel: CalendarViewModel
    // Mock repository
    private val mockRepository: TimeSlotRepository = mock()

    @Before
    fun setup() {
        viewModel = CalendarViewModel(timeSlotRepository = mockRepository)
    }

    @Test
    fun changeMonth_Success() = runTest {
        // Arrange
        val newMonth = YearMonth.of(2024, 10)
        val testDays = listOf(
            DayInfo(date = "2024-10-01", isSlotAvailable = true, isFullyBooked = false),
            DayInfo(date = "2024-10-02", isSlotAvailable = false, isFullyBooked = true)
        )


        val timeSlotResponse = TimeSlotResponse(
            start = "2024-10-01",
            end = "2024-10-31",
            days = testDays,
            totalDays = testDays.size
        )


        whenever(mockRepository.getTimeSlotsForRange("2024-10-01", "2024-10-31")).thenReturn(timeSlotResponse)

        // Act
        viewModel.changeMonth(newMonth)


        val uiState = viewModel.uiState.first { it.monthTimeSlots.isNotEmpty() }

        // Assert
        assertEquals(newMonth, uiState.currentMonth)
        assertEquals(testDays, uiState.monthTimeSlots)
    }

    @Test
    fun fetchTimeSlotsForDay_Succes() = runTest {
        // Arrange
        val selectedDate = LocalDate.of(2024, 10, 15)
        val testTimeSlots = listOf(
            TimeSlot(id = 1, start = "09:00", end = "10:00"),
            TimeSlot(id = 2, start = "11:00", end = "12:00")
        )
        whenever(mockRepository.getTimeSlotsForDay(2024, 10, 15)).thenReturn(testTimeSlots)

        // Act
        viewModel.selectDate(selectedDate)
        val uiState = viewModel.uiState.first { it.dailyTimeSlots.isNotEmpty() }

        // Assert
        assertEquals(selectedDate, uiState.selectedDate)
        assertEquals(testTimeSlots, uiState.dailyTimeSlots)
    }

    @Test
    fun fetchTimeSlotsForMonth_Error() = runTest {
        // Arrange
        val month = YearMonth.of(2024, 11)
        val errorMessage = "Network error"


        whenever(mockRepository.getTimeSlotsForRange(any(), any()))
            .thenAnswer { throw RuntimeException(errorMessage) }

        // Act
        viewModel.changeMonth(month)

        // Wait until monthErrorMessage is updated with the expected error message
        val uiState = viewModel.uiState.first {
            it.monthErrorMessage.isNotEmpty() && !it.isLoadingMonth
        }

        // Assert
        assertTrue(uiState.monthTimeSlots.isEmpty())
        assertEquals("Error: $errorMessage", uiState.monthErrorMessage)
    }

    @Test
    fun fetchTimeSlotsForDay_Error() = runTest {
        // Arrange
        val date = LocalDate.of(2024, 11, 5)
        val errorMessage = "Server unavailable"


        whenever(mockRepository.getTimeSlotsForDay(any(), any(), any()))
            .thenAnswer { throw RuntimeException(errorMessage) }

        // Act
        viewModel.selectDate(date)

        // Ensure all coroutines complete
        advanceUntilIdle()


        val uiState = viewModel.uiState.value

        // Assert
        assertTrue(uiState.dailyTimeSlots.isEmpty())
        assertEquals("Error: $errorMessage", uiState.dailyErrorMessage)
    }

    fun selectedTimeSlot_Success() = runTest {
        // Arrange
        val timeSlot = TimeSlot(id = 1, start = "10:00", end = "11:00")

        // Act
        viewModel.onTimeSlotSelected(timeSlot)
        val uiState = viewModel.uiState.value

        // Assert
        assertEquals(timeSlot, uiState.selectedTimeSlot)
    }

    @Test
    fun clearSelectedTimeSlot_Success() = runTest {
        // Arrange
        val timeSlot = TimeSlot(id = 1, start = "10:00", end = "11:00")
        viewModel.onTimeSlotSelected(timeSlot)

        // Act
        viewModel.onTimeSlotDismissed()
        val uiState = viewModel.uiState.value

        // Assert
        assertNull(uiState.selectedTimeSlot)
}
    }






