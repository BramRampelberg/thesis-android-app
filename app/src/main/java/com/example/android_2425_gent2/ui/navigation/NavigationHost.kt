package com.example.android_2425_gent2.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_2425_gent2.ui.screens.CalendarPage
import com.example.android_2425_gent2.ui.screens.HomePage
import com.example.android_2425_gent2.ui.screens.ProfilePage
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomePage()
        }
        composable(route = BottomNavItem.Calendar.route) {
            CalendarPage()
        }
        composable(route = BottomNavItem.Notifications.route) {
            ReservationsPage()
        }
        composable(route = BottomNavItem.Profile.route) {
            ProfilePage()
        }
    }
}