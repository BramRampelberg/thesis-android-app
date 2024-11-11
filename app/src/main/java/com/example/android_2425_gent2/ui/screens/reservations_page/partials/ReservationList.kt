package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.network.model.ReservationDto


@Composable
fun ReservationList(
    reservations: List<ReservationDto>,
    onSelectedReservationChange: (ReservationDto) -> Unit,
    onLoadMore: (Int) -> Unit,
    modifier: Modifier,
    isLoadingMore: Boolean = false
) {
    val listState = rememberLazyListState()

    // Check when we're close to the end
    LaunchedEffect(listState.firstVisibleItemIndex) {
        val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val totalItems = reservations.size

        // If we're within last 2 items and have items
        if (lastVisibleItem >= totalItems - 2 && totalItems > 0) {
            println("Triggering load more at index: $lastVisibleItem")
            onLoadMore(lastVisibleItem)
        }
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservations) { item ->
            ReservationCard(
                item,
                onSelectedReservationChange,
                modifier = modifier
            )
        }

        // Loading indicator
        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}