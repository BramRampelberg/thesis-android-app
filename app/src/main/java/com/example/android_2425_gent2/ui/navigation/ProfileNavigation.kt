package com.example.android_2425_gent2.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.android_2425_gent2.ui.screens.profile_page.AdminDashBoard
import com.example.android_2425_gent2.ui.screens.profile_page.GuestUsersScreen
import com.example.android_2425_gent2.ui.screens.profile_page.ProfilePage

object ProfileNavigation {
    const val PROFILE_ADMIN_DASHBOARD = "profile/admin-dashboard"
    const val PROFILE_ADMIN_USERS = "profile/admin-users"

}

fun NavGraphBuilder.profileNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable(route = BottomNavItem.Profile.route) {
        ProfilePage(
            modifier = modifier,
            onNavigateToDashboard = {
                navController.navigate(ProfileNavigation.PROFILE_ADMIN_DASHBOARD)
            }
        )
    }

    composable(route = ProfileNavigation.PROFILE_ADMIN_DASHBOARD) {
        AdminDashBoard(
            modifier = modifier,
            onNavigateToUsers = {
                navController.navigate(ProfileNavigation.PROFILE_ADMIN_USERS)
            }
            ,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }

    composable(route = ProfileNavigation.PROFILE_ADMIN_USERS) {
        GuestUsersScreen(
            modifier = modifier,
            onNavigateToUserDetails = {},
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }

}