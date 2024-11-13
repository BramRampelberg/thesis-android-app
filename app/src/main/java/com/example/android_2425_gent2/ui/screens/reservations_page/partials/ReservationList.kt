package com.example.android_2425_gent2.ui.screens.reservations_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.network.model.ReservationDto
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationListState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@Composable
fun ReservationList(
    state: ReservationListState,
    onSelectedReservationChange: (ReservationDto) -> Unit,
    onLoadMore: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember(listState, state) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = state.reservations.size
            lastVisibleItem >= totalItems - 2 && totalItems > 0 && !state.isLoadingMore
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            onLoadMore(lastVisibleItem)
        }
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = state.reservations,
            key = { it.id }
        ) { reservation ->
            ReservationCard(
                reservation = reservation,
                onSelectedReservationChange = onSelectedReservationChange,
                modifier = modifier
            )
        }

        if (state.isLoadingMore) {
            item(key = "loading") {
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