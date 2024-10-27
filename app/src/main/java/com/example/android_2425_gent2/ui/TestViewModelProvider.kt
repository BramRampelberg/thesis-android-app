package com.example.android_2425_gent2.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android_2425_gent2.TestApplication
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsViewModel

object TestViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ReservationsViewModel(testApplication().container.reservationRepository)
        }
    }
}

fun CreationExtras.testApplication(): TestApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TestApplication)
