package com.example.android_2425_gent2.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.ui.screens.calendar_page.CalendarViewModel
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsViewModel
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationViewModel
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ReservationsViewModel(mainApplication().container.reservationRepository)
        }

        initializer {
            CalendarViewModel(
                mainApplication().container.timeSlotRepository,
                mainApplication().container.reservationRepository
            )
        }

        initializer {
            NotificationDetailsViewModel(
                mainApplication().container.notificationRepository
            )
        }

        initializer {
            NotificationViewModel(mainApplication().container.notificationRepository)
        }
    }

}

fun CreationExtras.mainApplication(): MainApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
