package com.example.android_2425_gent2.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.network.model.NotificationDto
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsPage
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationPage

object NotificationNavigation {
    const val NOTIFICATION_ROUTE = "notifications"
    const val NOTIFICATION_DETAILS_ROUTE = "notification_details"
}

fun NavGraphBuilder.notificationNavigation(navController: NavController) {
    composable(route = BottomNavItem.Notifications.route) {
        NotificationPage(
            onNotificationClick = { notification ->
                navController.currentBackStackEntry?.savedStateHandle?.set("notification", notification)
                navController.navigate(NotificationNavigation.NOTIFICATION_DETAILS_ROUTE)
            }
        )
    }

    composable(
        route = NotificationNavigation.NOTIFICATION_DETAILS_ROUTE
    ) {
        val notification = navController.previousBackStackEntry?.savedStateHandle?.get<Notification>("notification")

        if (notification != null) {
            NotificationDetailsPage(
                notification = notification,
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel(factory = AppViewModelProvider.Factory)
            )
        }
    }
}