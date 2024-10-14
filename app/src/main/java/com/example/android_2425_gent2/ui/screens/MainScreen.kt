package com.example.android_2425_gent2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.ui.navigation.NavItem


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home, 0),
        NavItem("Calendar", Icons.Default.DateRange, 0),
        NavItem("Notification", Icons.Default.Notifications, 5),
        NavItem("Profile", Icons.Default.Person, 0)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, spotColor = Color.Black.copy(alpha = 0.1f), clip = false)
            ) {
                NavigationBar(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    containerColor = Color.White
                ) {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItem.badgeCount > 0) {
                                        Badge { Text(text = navItem.badgeCount.toString()) }
                                    }
                                }) {
                                    Icon(
                                        imageVector = navItem.icon,
                                        contentDescription = "Icon",
                                        tint = if (selectedIndex == index) Color(0xFF42C4BE) else Color.Gray
                                    )
                                }
                            },

                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF42C4BE),
                                unselectedIconColor = Color.Gray,
                                indicatorColor = Color.White
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}


@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int) {
    when(selectedIndex){
        0-> HomePage()
        1-> CalendarPage()
        2-> NotificationPage()
        3-> ProfilePage()
    }
}
