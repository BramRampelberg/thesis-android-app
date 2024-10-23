package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.reservations_page.partials.ReservationDetailsBottomModalSheet
import com.example.android_2425_gent2.ui.screens.reservations_page.partials.ReservationList
import com.example.android_2425_gent2.ui.screens.reservations_page.partials.ReservationTypeSelectionDropDownMenu
import kotlinx.coroutines.launch

@Preview
@Composable
fun ReservationsPage(
    viewModel: ReservationsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val reservationsUiState by viewModel.reservationsUiState.collectAsState()
    val selectedReservationUiState = viewModel.selectedReservationUiState
    var reservationType: ReservationType by remember { mutableStateOf(ReservationType.UPCOMING) }
    var selectedReservation: Reservation? by remember { mutableStateOf(null) }

    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ReservationTypeSelectionDropDownMenu(
            reservationType,
            {
                reservationType = it
            },
            modifier = modifier
        )
        ReservationList(
            reservationsUiState.reservations,
            {
                coroutineScope.launch {
                    viewModel.setSelectedReservation(it)
                }
            }, modifier
        )
        if (selectedReservationUiState.selectedReservation != null) {
            ReservationDetailsBottomModalSheet(
                selectedReservationUiState.selectedReservation,
                {
                    coroutineScope.launch {
                        viewModel.setSelectedReservation(it)
                    }
                },
                modifier
            )
        }
    }
}

enum class ReservationType(val title: Int) {
    OLD(title = R.string.old_reservations),
    UPCOMING(title = R.string.upcoming_reservations),
}