package com.example.android_2425_gent2.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    var badgeCount: Int
) {
    Home("home", Icons.Default.Home, "Home", 0),
    Calendar("calendar", Icons.Default.DateRange, "Calender", 0),
    Notifications("notifications", Icons.Default.Notifications, "Notificaties", 0),
    Profile("profile", Icons.Default.Person, "Profiel", 0)
}