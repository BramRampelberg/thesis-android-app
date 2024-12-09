package com.example.android_2425_gent2.ui.navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationViewModel
import java.util.Locale

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier,notificationViewModel: NotificationViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val unreadCount by notificationViewModel.unreadCount.collectAsState()
        
        BottomNavItem.entries.forEach { item ->
            if (item == BottomNavItem.Notifications) {
                item.badgeCount = unreadCount
            }
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    BadgedBox(badge = {
                        if (item.badgeCount > 0) {
                            Badge { Text(text = item.badgeCount.toString()) }
                        }
                    }) {
                        Icon(
                            item.icon,
                            contentDescription = null
                        )
                    }
                },
                label = {
                    Text(stringResource(item.labelStringResourceId).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    })
                }
            )
        }
    }
}