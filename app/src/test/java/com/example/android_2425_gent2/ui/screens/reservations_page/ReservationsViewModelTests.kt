package com.example.android_2425_gent2.ui.screens.reservations_page

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import com.example.android_2425_gent2.data.network.model.ReservationDto
import com.example.android_2425_gent2.data.network.model.ReservationResponse
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import com.example.android_2425_gent2.ui.screens.reservations_page.coroutine.MainDispatcherRule
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalTime
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class ReservationsViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val reservationRepository: ReservationRepository = mock()
    private lateinit var viewModel: ReservationsViewModel

    private val sampleDate = LocalDate.of(2024, 1, 1)
    private val sampleStartTime = LocalTime.of(10, 0)
    private val sampleEndTime = LocalTime.of(12, 0)

    private val sampleReservation1 = ReservationDto(
        start = sampleStartTime,
        end = sampleEndTime,
        date = sampleDate,
        boatId = 1,
        boatPersonalName = "Boat 1",
        id = 1
    )

    private val sampleReservation2 = ReservationDto(
        start = sampleStartTime.plusHours(2),
        end = sampleEndTime.plusHours(2),
        date = sampleDate,
        boatId = 2,
        boatPersonalName = "Boat 2",
        id = 2
    )

    @Test
    fun `initial state should be loading upcoming reservations`() = runTest {

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = emptyList(),
                nextId = null,
                previousId = null,
                isFirstPage = true
            )))
        })


        viewModel = ReservationsViewModel(reservationRepository)


        assertEquals(ReservationType.UPCOMING, viewModel.reservationTypeUiState.reservationType)
        assertTrue(viewModel.reservationsUiState.value.loading)
    }

    @Test
    fun `setReservationType should update state and trigger loading`() = runTest {

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = emptyList(),
                nextId = null,
                previousId = null,
                isFirstPage = true
            )))
        })

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = true,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = emptyList(),
                nextId = null,
                previousId = null,
                isFirstPage = true
            )))
        })

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()


        viewModel.setReservationType(ReservationType.OLD)
        advanceUntilIdle()


        assertEquals(ReservationType.OLD, viewModel.reservationTypeUiState.reservationType)
    }

    @Test
    fun `successful reservation load should update state correctly`() = runTest {

        val mockReservations = listOf(sampleReservation1, sampleReservation2)

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(
                ReservationResponse(
                data = mockReservations,
                nextId = 3,
                previousId = null,
                isFirstPage = true
            )
            ))
        })


        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()


        with(viewModel.reservationsUiState.value) {
            assertFalse(loading)
            assertFalse(hasError)
            assertEquals(mockReservations, reservations)
            assertEquals(3, nextCursor)
            assertNull(previousCursor)
            assertTrue(isFirstPage)
        }
    }

    @Test
    fun `error during reservation load should update error state`() = runTest {

        val errorMessage = "Network error"

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Error(errorMessage))
        })

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

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = initialReservations,
                nextId = 3,
                previousId = null,
                isFirstPage = true
            )))
        })

        whenever(reservationRepository.getReservations(
            cursor = 3,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = listOf(sampleReservation2),
                nextId = null,
                previousId = 2,
                isFirstPage = false
            )))
        })


        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()

        viewModel.loadMoreIfNeeded(1)
        advanceUntilIdle()


        verify(reservationRepository).getReservations(
            cursor = 3,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )
    }

    @Test
    fun `setSelectedReservation should update selected reservation state`() = runTest {

        whenever(reservationRepository.getReservations(
            cursor = null,
            isNextPage = true,
            getPast = false,
            pageSize = 10
        )).thenReturn(flow {
            emit(APIResource.Success(ReservationResponse(
                data = emptyList(),
                nextId = null,
                previousId = null,
                isFirstPage = true
            )))
        })

        viewModel = ReservationsViewModel(reservationRepository)
        advanceUntilIdle()


        viewModel.setSelectedReservation(sampleReservation1)

        assertEquals(sampleReservation1, viewModel.selectedReservationUiState.selectedReservation)
    }
}
