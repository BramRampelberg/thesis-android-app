package com.example.android_2425_gent2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.profile_page.ProfilePage
import com.example.android_2425_gent2.ui.screens.profile_page.ProfilePageViewModel
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsPage
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier,
    logout: () -> Unit,
    reservationsViewModel: ReservationsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    profileViewModel: ProfilePageViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Reservations.route,
        modifier = modifier
    ) {
        composable(route = BottomNavItem.Reservations.route) {
            ReservationsPage(viewModel = reservationsViewModel)
        }
        composable(route = BottomNavItem.Profile.route) {
            ProfilePage(
                modifier = modifier,
                logout = logout,
                viewModel = profileViewModel,
            )
        }
    }
}