package com.example.android_2425_gent2.ui.screens.reservations_page

import com.example.android_2425_gent2.data.model.OfflineReservation
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import com.example.android_2425_gent2.ui.screens.reservations_page.coroutine.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class ReservationsViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val reservationRepository: ReservationRepository = mockk()
    private lateinit var viewModel: ReservationsViewModel

    private val sampleDate = LocalDate.of(2024, 1, 1)
    private val sampleStartTime = LocalTime.of(10, 0)
    private val sampleEndTime = LocalTime.of(12, 0)

    private val sampleReservation1 = OfflineReservation(
        start = sampleStartTime,
        end = sampleEndTime,
        date = sampleDate,
        boatId = 1,
        boatPersonalName = "Boat 1",
        id = 1
    )

    private val sampleReservation2 = OfflineReservation(
        start = sampleStartTime.plusHours(2),
        end = sampleEndTime.plusHours(2),
        date = sampleDate,
        boatId = 2,
        boatPersonalName = "Boat 2",
        id = 2
    )

    @Test
    fun `initial state should be loading upcoming reservations`() = runTest {
        coEvery { reservationRepository.getReservations(
            getPast = false,
        ) } returns flow {
            emit(APIResource.Success(emptyList()))
        }

        viewModel = ReservationsViewModel(reservationRepository)

        assertEquals(ReservationType.UPCOMING, viewModel.reservationTypeUiState.reservationType)
        assertTrue(viewModel.reservationsUiState.value.loading)
    }

    @Test
    fun `setReservationType should update state and trigger loading`() = runTest {
        // Use every {} for mockk
        coEvery { reservationRepository.getReservations(

            getPast = false,

        ) } returns flow {
            emit(APIResource.Success(emptyList()))
        }

        coEvery { reservationRepository.getReservations(

            getPast = true,

        ) } returns flow {
            emit(APIResource.Success(emptyList()))
        }

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()

        viewModel.setReservationType(ReservationType.OLD)
        advanceUntilIdle()

        assertEquals(ReservationType.OLD, viewModel.reservationTypeUiState.reservationType)
    }

    @Test
    fun `successful reservation load should update state correctly`() = runTest {
        val mockReservations = listOf(sampleReservation1, sampleReservation2)

        coEvery { reservationRepository.getReservations(
            getPast = false,

        ) } returns flow {
            emit(APIResource.Success(

                    mockReservations

                )
            )
        }

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()

        with(viewModel.reservationsUiState.value) {
            assertFalse(loading)
            assertFalse(hasError)
            assertEquals(mockReservations, reservations)
        }
    }

    @Test
    fun `error during reservation load should update error state`() = runTest {
        val errorMessage = "Network error"

        // Use every {} for mockk
        coEvery { reservationRepository.getReservations(
            getPast = false,
        ) } returns flow {
            emit(APIResource.Error(errorMessage))
        }

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()

        with(viewModel.reservationsUiState.value) {
            assertFalse(loading)
            assertTrue(hasError)
            assertEquals(errorMessage, errorMessage)
        }
    }

    @Test
    fun `loadMoreIfNeeded should trigger load when conditions are met`() = runTest {
        val initialReservations = listOf(sampleReservation1, sampleReservation2)

        // Use every {} for mockk
        coEvery { reservationRepository.getReservations(

            getPast = false,
        ) } returns flow {
            emit(APIResource.Success(initialReservations))
        }

        coEvery { reservationRepository.getReservations(
            getPast = false,

        ) } returns flow {
            emit(APIResource.Success(listOf(sampleReservation2)))
        }

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()


    }

    @Test
    fun `setSelectedReservation should update selected reservation state`() = runTest {
        // Use every {} for mockk
        coEvery { reservationRepository.getReservations(

            getPast = false,

        ) } returns flow {
            emit(APIResource.Success(emptyList()))
        }

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()

        viewModel.setSelectedReservation(sampleReservation1)

        assertEquals(sampleReservation1, viewModel.selectedReservationUiState.selectedReservation)
    }
}
