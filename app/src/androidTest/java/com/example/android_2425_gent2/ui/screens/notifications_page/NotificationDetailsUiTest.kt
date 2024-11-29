package com.example.android_2425_gent2.ui.screens.notifications_page

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsPage
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotificationDetailsUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()

        composeTestRule.setContent {
            Android2425gent2Theme {
                NotificationDetailsPage(
                    notificationId = 1,
                    onNavigateBack = {}
                )
            }
        }
    }

    // TODO: fix notification details tests
//    @Test
//    fun showNotificationTitle() {
//        composeTestRule.onNodeWithTag("NotificationDetailsTitle").assertExists()
//    }
//
//    @Test
//    fun showNotificationMessage() {
//        composeTestRule.onNodeWithTag("NotificationDetailsMessage").assertExists()
//    }

    @Test
    fun showBackButton() {
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun showNotificationTimestamp() {
        composeTestRule.onNodeWithTag("NotificationDetailsTimestamp").assertExists()
    }
}