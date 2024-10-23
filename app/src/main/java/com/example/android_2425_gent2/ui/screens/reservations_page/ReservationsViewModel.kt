package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.repository.ReservationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ReservationsViewModel(private val reservationRepository: ReservationRepository) :
    ViewModel() {

    val reservationsUiState: StateFlow<ReservationsUiState> =
        reservationRepository.getAllReservationsStream().map { ReservationsUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ReservationsUiState(),
            )

    var selectedReservationUiState by mutableStateOf(SelectedReservationUiState(null))
        private set

    suspend fun setSelectedReservation(reservation: Reservation?) {
        selectedReservationUiState = SelectedReservationUiState(reservation)
    }
}

data class ReservationsUiState(
    val reservations: List<Reservation> = listOf(),
)

data class SelectedReservationUiState(
    val selectedReservation: Reservation?,
)