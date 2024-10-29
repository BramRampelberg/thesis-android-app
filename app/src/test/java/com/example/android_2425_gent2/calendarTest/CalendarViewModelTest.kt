package com.example.android_2425_gent2.calendarTest

import com.example.android_2425_gent2.calendarTest.coroutine.MainDispatcherRule
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlotResponse
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.ui.screens.calendar_page.CalendarViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
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


}
