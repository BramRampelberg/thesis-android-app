package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import com.example.android_2425_gent2.data.network.model.ReservationDetailsDto
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        onDismissRequest = {
            onSelectedReservationChange(null)
        },
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.padding(16.dp)
        ) {
            Column {
                Text(stringResource(R.string.reservation_details), fontSize = 32.sp)
                Box(modifier.height(16.dp))
                ReservationDateText(selectedReservation, modifier = modifier)
                ReservationTimeSlotText(selectedReservation, modifier = modifier)
                ReservationBoatText(selectedReservation, modifier = modifier)
                Spacer(modifier.height(20.dp))

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (reservationDetails != null) {
                    Text("Naam ophaal persoon", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(reservationDetails.pickupPersonName)
                    Spacer(modifier.height(16.dp))

                    Text("Contact gegevens", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(reservationDetails.phoneNumber)
                    Text(reservationDetails.email)
                    Spacer(modifier.height(16.dp))

                    Text("Adres", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(reservationDetails.street)
                    Text("${reservationDetails.postalCode} ${reservationDetails.city}")
                    Spacer(modifier.height(16.dp))

                    Text("Meter/Peter", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(reservationDetails.mentorName)
                }
            }
            Spacer(modifier.height(60.dp))
            ElevatedButton(
                onClick = { },
                colors = ButtonColors(
                    Color(0xFFC44244),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black,
                ),
                enabled = false,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.cancel_reservation))
            }
        }
    }
}