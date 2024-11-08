package com.example.android_2425_gent2.ui.screens.reservations_page

import app.cash.turbine.test
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.repository.ReservationRepository
import com.example.android_2425_gent2.ui.screens.calendar_page.coroutine.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.stub
import org.mockito.kotlin.whenever

@RunWith(Parameterized::class)
class ReservationsViewModelTests(private val reservationType: ReservationType) {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: ReservationsViewModel

    @Mock
    lateinit var mockRepository: ReservationRepository

    private val reservationsFlow = MutableStateFlow<List<Reservation>>(emptyList())

    companion object {
        @JvmStatic
        @Parameterized.Parameters()
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(ReservationType.UPCOMING),
                arrayOf(ReservationType.OLD),
                arrayOf(ReservationType.CANCELED),
            )
        }
    }

    @Before
    fun setup() {
        mockRepository.stub {
            onBlocking { getAllReservationsStream() } doAnswer { reservationsFlow }
        }
        mockRepository.stub {
            onBlocking { getAllUpcomingReservationsStream() } doAnswer { reservationsFlow }
        }
        mockRepository.stub {
            onBlocking { getAllPastReservationsStream() } doAnswer { reservationsFlow }
        }
//        whenever(mockRepository.getAllUpcomingReservationsStream())
//            .thenReturn(reservationsFlow)
//        whenever(mockRepository.getAllPastReservationsStream())
//            .thenReturn(reservationsFlow)
//        whenever(mockRepository.getAllReservationsStream())
//            .thenReturn(reservationsFlow)
        viewModel = ReservationsViewModel(
            reservationRepository = mockRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `reservationsUiState emits loading then reservations`() = runTest {
        val reservations = listOf(
            Reservation(
                id = 1,
                boat = null,
                battery = null,
                timeSlot = null
            )
        )
        viewModel.reservationsUiState.test {
            viewModel.loadReservationsForCurrentType()
            assertEquals(ReservationsUiState(loading = true), awaitItem())

            reservationsFlow.emit(reservations)
            assertEquals(
                ReservationsUiState(reservations = reservations, loading = false),
                awaitItem()
            )

            cancelAndConsumeRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `reservationsUiState emits error state on exception`() = runTest {
        whenever(mockRepository.getAllUpcomingReservationsStream())
            .thenReturn(flow {
                throw Exception("Test exception")
            })
        whenever(mockRepository.getAllPastReservationsStream())
            .thenReturn(flow {
                throw Exception("Test exception")
            })
        whenever(mockRepository.getAllReservationsStream())
            .thenReturn(flow {
                throw Exception("Test exception")
            })


        viewModel.reservationsUiState.test {
            viewModel.loadReservationsForCurrentType()
            assertEquals(ReservationsUiState(loading = true), awaitItem())

            assertEquals(ReservationsUiState(hasError = true), awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }
}
