package com.example.android_2425_gent2.ui.screens.calendar_page.partials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonColors
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.ui.screens.calendar_page.ReservationState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSlotReservationConfirmationSheet(
    timeSlot: TimeSlot,
    reservationState: ReservationState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    reservationErrorMessage: String = ""
) {
    ModalBottomSheet(
        onDismissRequest = {
            if (reservationState != ReservationState.PAYMENT_LOADING) {
                onDismiss()
            }
        },
        sheetState = rememberModalBottomSheetState(

            skipPartiallyExpanded = true
        )
    ) {
        when (reservationState) {
            ReservationState.PAYMENT_LOADING -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF42C4BE),
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.processing_payment),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.wait_for_process_your_payment),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }

            ReservationState.ERROR -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.reservation_failed),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = reservationErrorMessage.ifEmpty { stringResource(R.string.error_while_processing_reservation) },
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ElevatedButton(
                        onClick = { onDismiss() },
                        colors = ButtonColors(
                            Color.Red,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Close")
                    }
                }
            }

            ReservationState.CONFIRMATION -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = stringResource(R.string.success),
                        tint = Color(0xFF42C4BE),
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.reservation_confirmed),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reservation details
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                            .padding(16.dp)
                    ) {
                        val startTime = LocalTime.parse(timeSlot.start, DateTimeFormatter.ISO_TIME)
                        val endTime = LocalTime.parse(timeSlot.end, DateTimeFormatter.ISO_TIME)

                        Text(
                            text = stringResource(R.string.reservation_details),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val time = stringResource(R.string.time) +
                                ":${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
                                ":${endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"

                        val name = stringResource(R.string.name) + "Phillipe van Achter"

                        Text(time)
                        Text(name)

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ElevatedButton(
                        onClick = { onDismiss() },
                        colors = ButtonColors(
                            Color(0xFF42C4BE),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.close_reservation_details))
                    }
                }
            }
            else -> { }
        }
    }
}
