package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservationsViewModel(private val reservationRepository: ReservationRepository) :
    ViewModel() {

    var reservationTypeUiState by mutableStateOf(ReservationTypeUiSate(ReservationType.UPCOMING))
        private set

    fun setReservationType(reservationType: ReservationType) {
        reservationTypeUiState = ReservationTypeUiSate(reservationType)
        loadReservationsForCurrentType()
    }

    private val _reservationsUiState: MutableStateFlow<ReservationsUiState> =
        MutableStateFlow(ReservationsUiState(loading = true))

    val reservationsUiState: StateFlow<ReservationsUiState> = _reservationsUiState

    var selectedReservationUiState by mutableStateOf(SelectedReservationUiState(null))
        private set

    suspend fun setSelectedReservation(reservation: Reservation?) {
        selectedReservationUiState = SelectedReservationUiState(reservation)
    }

    private fun loadReservationsForCurrentType() {
        viewModelScope.launch {
            getStateFlowForReservationType(reservationTypeUiState.reservationType)
                .collect { uiState ->
                    _reservationsUiState.value = uiState
                }
        }
    }

    private fun getStateFlowForReservationType(reservationType: ReservationType): StateFlow<ReservationsUiState> {
        val flow = when (reservationType) {
            ReservationType.UPCOMING -> reservationRepository.getAllUpcomingReservationsStream()
            ReservationType.OLD -> reservationRepository.getAllPastReservationsStream()
            ReservationType.CANCELED -> reservationRepository.getAllReservationsStream()
        }
        return reservationFlowWithLoading(flow)
    }

    private fun reservationFlowWithLoading(flow: Flow<List<Reservation>>): StateFlow<ReservationsUiState> {
        return flow
            .map { ReservationsUiState(reservations = it, loading = false) }
            .onStart {
                emit(ReservationsUiState(loading = true))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ReservationsUiState(loading = true)
            )
    }

    init {
        loadReservationsForCurrentType()
    }
}

data class ReservationsUiState(
    val reservations: List<Reservation> = emptyList(),
    val loading: Boolean = false,
)

data class SelectedReservationUiState(
    val selectedReservation: Reservation?,
)

data class ReservationTypeUiSate(
    val reservationType: ReservationType,
)
