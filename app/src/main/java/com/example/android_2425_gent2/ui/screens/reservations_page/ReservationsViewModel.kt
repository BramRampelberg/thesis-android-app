package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.lifecycle.ViewModel
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.repository.ReservationRepository

class ReservationsViewModel(private val reservationRepository: ReservationRepository) :
    ViewModel() {

//    val reservationsSate: StateFlow<ReservationsUiState> =
//        reservationRepository.getReservationsByUserStream(1)
}

data class ReservationsUiState(val reservations: List<Reservation> = listOf())