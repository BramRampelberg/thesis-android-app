package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.model.OfflineReservation
import com.example.android_2425_gent2.data.network.model.ReservationDto
import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ReservationsViewModel"
//the amount of reservations we want to load at a time
//when
class ReservationsViewModel(private val reservationRepository: ReservationRepository) : ViewModel() {
    var reservationTypeUiState by mutableStateOf(ReservationTypeUiSate(ReservationType.UPCOMING))
        private set

    private val _reservationsUiState = MutableStateFlow(ReservationsUiState(loading = true))
    val reservationsUiState: StateFlow<ReservationsUiState> = _reservationsUiState

    var selectedReservationUiState by mutableStateOf(SelectedReservationUiState(null))
        private set

    fun setReservationType(reservationType: ReservationType) {
        reservationTypeUiState = ReservationTypeUiSate(reservationType)
        loadReservationsForCurrentType()
    }

    init {
        loadReservationsForCurrentType()
    }



    fun setSelectedReservation(reservation: OfflineReservation?) {
        selectedReservationUiState = SelectedReservationUiState(reservation)
        if (reservation != null) {
            loadReservationDetails(reservation.id)
        }
    }

    private fun loadReservationsForCurrentType() {
        viewModelScope.launch {
            val getPast = when (reservationTypeUiState.reservationType) {
                ReservationType.UPCOMING -> false
                ReservationType.OLD -> true
                ReservationType.CANCELED -> false
            }

            reservationRepository.getReservations(getPast = getPast).collect { apiResource ->
                when (apiResource) {
                    is APIResource.Loading -> {
                        _reservationsUiState.value = ReservationsUiState(loading = true)
                    }
                    is APIResource.Success -> {
                        val reservations = apiResource.data
                        if (reservations != null) {
                            _reservationsUiState.value = ReservationsUiState(
                                reservations = reservations,
                                loading = false
                            )
                        } else {
                            _reservationsUiState.value = ReservationsUiState(
                                hasError = true,
                                errorMessage = "No data available"
                            )
                        }
                    }
                    is APIResource.Error -> {
                        _reservationsUiState.value = ReservationsUiState(
                            hasError = true,
                            errorMessage = apiResource.message,
                            loading = false
                        )
                    }
                }
            }
        }
    }
}

data class ReservationsUiState(
    val reservations: List<OfflineReservation> = emptyList(),
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

data class SelectedReservationUiState(
    val selectedReservation: OfflineReservation?
    val selectedReservation: ReservationDto?,
    val details: ReservationDetailsDto? = null,
    val isLoadingDetails: Boolean = false,
    val error: String? = null
)

data class ReservationTypeUiSate(
    val reservationType: ReservationType
)
