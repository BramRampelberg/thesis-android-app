package com.example.android_2425_gent2.ui.screens.notifications_page

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
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

    @Test
    fun showNotificationTitle() {
        // Since we're using a test notification with title "This is a info notification"
        composeTestRule.onNodeWithText("This is a info notification").assertExists()
    }

    @Test
    fun showNotificationMessage() {
        // Since we're using a test notification with a specific message
        composeTestRule.onNodeWithText("This is the message").assertExists()
    }

    @Test
    fun showBackButton() {
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }

    @Test
    fun showNotificationTimestamp() {
        composeTestRule.onNodeWithText("Just now", substring = true).assertExists()
    }
}