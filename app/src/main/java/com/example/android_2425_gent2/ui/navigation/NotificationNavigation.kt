package com.example.android_2425_gent2.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsPage
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationPage

object NotificationNavigation {
    const val NOTIFICATION_DETAILS_ROUTE = "notifications/{notificationId}"

    fun notificationDetailsRoute(notificationId: Int): String {
        return "notifications/$notificationId"
    }
}

fun NavGraphBuilder.notificationNavigation(navController: NavController) {
    composable(route = BottomNavItem.Notifications.route) {
        NotificationPage(
            onNotificationClick = { notificationId ->
                navController.navigate(NotificationNavigation.notificationDetailsRoute(notificationId))
            }
        )
    }

    composable(
        route = NotificationNavigation.NOTIFICATION_DETAILS_ROUTE,
        arguments = listOf(
            navArgument("notificationId") {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->
        val notificationId = backStackEntry.arguments?.getInt("notificationId") ?: return@composable

        NotificationDetailsPage(
            notificationId = notificationId,
            onNavigateBack = { navController.popBackStack() },
            viewModel = viewModel(factory = AppViewModelProvider.Factory)
        )
    }
}