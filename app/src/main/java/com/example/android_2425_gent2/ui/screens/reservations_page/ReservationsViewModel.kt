package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_2425_gent2.data.network.model.ReservationDto
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.reservation.ReservationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "ReservationsViewModel"
private const val PAGE_SIZE = 10

class ReservationsViewModel(private val reservationRepository: ReservationRepository) :
    ViewModel() {
    var reservationTypeUiState by mutableStateOf(ReservationTypeUiSate(ReservationType.UPCOMING))
        private set

    private val _reservationsUiState: MutableStateFlow<ReservationsUiState> =
        MutableStateFlow(ReservationsUiState(loading = true))

    val reservationsUiState: StateFlow<ReservationsUiState> = _reservationsUiState

    var selectedReservationUiState by mutableStateOf(SelectedReservationUiState(null))
        private set

    private var currentReservations = mutableListOf<ReservationDto>()

    fun setReservationType(reservationType: ReservationType) {
        reservationTypeUiState = ReservationTypeUiSate(reservationType)
        currentReservations.clear()
        loadReservationsForCurrentType(cursor = null, isNextPage = true)
    }

    suspend fun setSelectedReservation(reservation: ReservationDto?) {
        selectedReservationUiState = SelectedReservationUiState(reservation)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun loadReservationsForCurrentType(
        cursor: Int? = null,
        isNextPage: Boolean = true
    ) {
        viewModelScope.launch {
            val getPast = when (reservationTypeUiState.reservationType) {
                ReservationType.UPCOMING -> false
                ReservationType.OLD -> true
                ReservationType.CANCELED -> false
            }

            println("Loading reservations: cursor=$cursor, isNextPage=$isNextPage, getPast=$getPast")

            reservationRepository.getReservations(
                cursor = cursor,
                isNextPage = isNextPage,
                getPast = getPast,
                pageSize = PAGE_SIZE
            ).collect { apiResource ->
                println("Received API response: $apiResource")
                when (apiResource) {
                    is APIResource.Loading -> {
                        if (cursor == null) {
                            _reservationsUiState.value = ReservationsUiState(loading = true)
                        } else {
                            _reservationsUiState.value = _reservationsUiState.value.copy(isLoadingMore = true)
                        }
                    }
                    is APIResource.Success -> {
                        val response = apiResource.data
                        if (response != null) {
                            if (cursor == null) {
                                currentReservations.clear()
                            }
                            currentReservations.addAll(response.data)

                            _reservationsUiState.value = ReservationsUiState(
                                reservations = currentReservations.toList(),
                                loading = false,
                                isLoadingMore = false,
                                nextCursor = response.nextId,
                                previousCursor = response.previousId,
                                isFirstPage = response.isFirstPage
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
                            reservations = currentReservations,
                            hasError = true,
                            errorMessage = apiResource.message,
                            loading = false,
                            isLoadingMore = false
                        )
                        println("Error loading reservations: ${apiResource.message}")
                    }
                }
            }
        }
    }

    fun loadMoreIfNeeded(lastVisibleIndex: Int) {
        println("loadMoreIfNeeded called with index: $lastVisibleIndex")
        val currentState = _reservationsUiState.value

        if (!currentState.loading &&
            !currentState.isLoadingMore &&
            currentState.nextCursor != null &&
            lastVisibleIndex >= currentReservations.size - 2
        ) {
            println("Loading more with cursor: ${currentState.nextCursor}")
            loadReservationsForCurrentType(
                cursor = currentState.nextCursor,
                isNextPage = true
            )
        } else {
            println("Skipping load more - conditions not met: loading=${currentState.loading}, " +
                    "loadingMore=${currentState.isLoadingMore}, " +
                    "nextCursor=${currentState.nextCursor}")
        }
    }

    fun refresh() {
        currentReservations.clear()
        loadReservationsForCurrentType(cursor = null, isNextPage = true)
    }

    init {
        loadReservationsForCurrentType()
    }
}

data class ReservationsUiState(
    val reservations: List<ReservationDto> = emptyList(),
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val nextCursor: Int? = null,
    val previousCursor: Int? = null,
    val isFirstPage: Boolean = true,
    val isLoadingMore: Boolean = false
)

data class SelectedReservationUiState(
    val selectedReservation: ReservationDto?
)

data class ReservationTypeUiSate(
    val reservationType: ReservationType
)