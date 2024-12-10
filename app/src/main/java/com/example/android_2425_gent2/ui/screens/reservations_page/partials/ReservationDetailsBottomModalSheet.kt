package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.network.model.ReservationDto
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
                    // Check if any essential details are null or blank
                    val hasValidDetails = !(
                        reservationDetails.currentBatteryUserName.isNullOrBlank() || 
                        reservationDetails.currentHolderPhoneNumber.isNullOrBlank() || 
                        reservationDetails.currentHolderEmail.isNullOrBlank() ||
                        reservationDetails.currentHolderStreet.isNullOrBlank() || 
                        reservationDetails.currentHolderNumber.isNullOrBlank() || 
                        reservationDetails.currentHolderCity.isNullOrBlank() || 
                        reservationDetails.currentHolderPostalCode.isNullOrBlank()
                    )

                    if (hasValidDetails) {
                        ReservationDetailsPersonInfoSection(reservationDetails, modifier)
                        Spacer(modifier.height(16.dp))
                        ReservationDetailsAddressSection(reservationDetails, modifier)
                        
                        if (!reservationDetails.mentorName.isNullOrBlank()) {
                            Spacer(modifier.height(16.dp))
                            ReservationDetailsMentor(reservationDetails.mentorName, modifier)
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = colorResource(R.color.primary)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Geen ophaal informatie beschikbaar",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = colorResource(R.color.secondary_contrast_text)
                            )
                        }
                        Spacer(modifier.height(32.dp))
                    }
                }
            }

            CancelReservationButton(
                enabled = false,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
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
        Text(
            text = "Gegevens ophaal persoon",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary)
        )
        Text("Naam: ${details.currentBatteryUserName}")
        Text("Tel.: ${details.currentHolderPhoneNumber}")
        Text("E-mail: ${details.currentHolderEmail}")
    }
}

@Composable
fun ReservationDetailsAddressSection(
    details: ReservationDetailsDto,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Adres",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.primary)
        )
        Text("${details.currentHolderStreet} ${details.currentHolderNumber}")
        Text("${details.currentHolderPostalCode} ${details.currentHolderCity}")
    }
}