package com.example.android_2425_gent2.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun AdaptiveNavigationBar(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    when (windowSize) {
        WindowWidthSizeClass.Expanded -> {
            SideNavigationRail(
                navController = navController,
                modifier = modifier
            )

        }

        else -> {

            BottomNavigationBar(
                navController = navController,
                modifier = modifier
            )
        }
    }
}
