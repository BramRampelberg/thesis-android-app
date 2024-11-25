package com.example.android_2425_gent2.ui.screens.notifications_page

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationPage
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotificationsUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()

        composeTestRule.setContent {
            Android2425gent2Theme {
                NotificationPage()
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
}