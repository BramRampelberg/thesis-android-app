package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.network.model.ReservationDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailsBottomModalSheet(
    selectedReservation: ReservationDto,
    reservationDetails: ReservationDetailsDto?,
    isLoading: Boolean,
    onSelectedReservationChange: (ReservationDto?) -> Unit,
    modifier: Modifier
) {
    ModalBottomSheet(
        onDismissRequest = { onSelectedReservationChange(null) },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.padding(16.dp)
        ) {
            ReservationDetailsHeader(stringResource(R.string.reservation_details))
            
            Column {
                ReservationDateText(selectedReservation, modifier)
                ReservationTimeSlotText(selectedReservation, modifier)
                ReservationBoatText(selectedReservation, modifier)
                Spacer(modifier.height(20.dp))

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (reservationDetails != null) {
                    ReservationDetailsPersonInfoSection(reservationDetails, modifier)
                    Spacer(modifier.height(16.dp))
                    ReservationDetailsAddressSection(reservationDetails, modifier)
                    Spacer(modifier.height(16.dp))
                    ReservationDetailsMentor(reservationDetails.mentorName, modifier)
                }
            }

            CancelReservationButton(
                enabled = false,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ReservationDetailsPersonInfoSection(
    details: ReservationDetailsDto,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text("Current Holder Information:")
        Text("Name: ${details.currentBatteryUserName}")
        Text("Phone: ${details.currentHolderPhoneNumber}")
        Text("Email: ${details.currentHolderEmail}")
    }
}

@Composable
fun ReservationDetailsAddressSection(
    details: ReservationDetailsDto,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text("Address:")
        Text("Street: ${details.currentHolderStreet} ${details.currentHolderNumber}")
        Text("City: ${details.currentHolderCity}")
        Text("Postal Code: ${details.currentHolderPostalCode}")
    }
}