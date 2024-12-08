package com.example.android_2425_gent2.ui.screens.notifications_page

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.repository.notification.TestNotificationRepository
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.navigation.NotificationNavigation
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsPage
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationPage
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.util.Log

class NotificationsUiTests {
    private lateinit var testContainer: TestContainer

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(DelicateCoroutinesApi::class)
    @Before
    fun setContainer() {
        testContainer = TestContainer()
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = testContainer

        composeTestRule.setContent {
            Android2425gent2Theme {
                TestNotificationNavigation()
            }
        }
    }

    @Composable
    private fun TestNotificationNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = NotificationNavigation.NOTIFICATION_ROUTE,
            modifier = Modifier
        ) {
            composable(route = NotificationNavigation.NOTIFICATION_ROUTE) {
                NotificationPage(
                    onNotificationClick = { notification ->
                        // First handle the navigation
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "notification",
                            notification
                        )
                        navController.navigate(NotificationNavigation.NOTIFICATION_DETAILS_ROUTE)

                        // Then mark as read
                        GlobalScope.launch {
                            try {
                                Log.d("NotificationTest", "Attempting to mark notification ${notification.id} as read")
                                (testContainer.notificationRepository as TestNotificationRepository)
                                    .markNotificationAsRead(notification.id)
                                    .collect {
                                        Log.d("NotificationTest", "Successfully marked notification as read")
                                    }
                            } catch (e: Exception) {
                                Log.e("NotificationTest", "Error marking notification as read", e)
                            }
                        }
                    }
                )
            }

            composable(route = NotificationNavigation.NOTIFICATION_DETAILS_ROUTE) {
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
    }

    @Test
    fun showTitle() {
        composeTestRule.onNodeWithTag("NotificationPageTitle").assertExists()
    }

    @Test
    fun showList() {
        composeTestRule.onNodeWithTag("NotificationPageList").assertExists()
    }

    @Test
    fun unreadIndicatorDisappearsAfterReading() {
        // Verify that the list is being loaded
        composeTestRule.onNodeWithTag("NotificationPageList").assertExists()

        // Verify unread indicator exists initially
        composeTestRule.onNodeWithTag("unread_indicator_1", useUnmergedTree = true).assertExists()

        // Click the notification
        composeTestRule.onNodeWithTag("notification_1").performClick()

        // Verify that the details page is being loaded
        composeTestRule.onNodeWithTag("notification_details_page").assertExists()

        // Click back
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Verify that the list is being loaded
        composeTestRule.onNodeWithTag("NotificationPageList").assertExists()

        // Verify unread indicator is gone
        composeTestRule
            .onNodeWithTag("unread_indicator_1", useUnmergedTree = true)
            .assertDoesNotExist()
    }
}