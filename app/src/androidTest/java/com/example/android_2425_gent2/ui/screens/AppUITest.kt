package com.example.android_2425_gent2.ui.screens;

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.App
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppUITest {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()


    }

    fun setContent(isLoggedIn: Boolean = false){
        composeTestRule.setContent {
            Android2425gent2Theme {
                App(isLoggedIn = isLoggedIn)
            }
        }
    }

    @Test
    fun showsLoginPageWhenNotLoggedIn() {
        setContent()
        composeTestRule.onNodeWithTag("LoginPage").assertExists()
        composeTestRule.onNodeWithTag("MainScreen").assertDoesNotExist()
    }

    @Test
    fun showsMainScreenWhenLoggedIn() {
        setContent(isLoggedIn = true)
        composeTestRule.onNodeWithTag("LoginPage").assertDoesNotExist()
        composeTestRule.onNodeWithTag("MainScreen").assertExists()
    }

    @Test
    fun showsMainScreenWhenAfterLoggingIn() {
        setContent()
        composeTestRule.onNodeWithTag("EmailField")
            .performClick()
            .performTextInput("example@email.com")

        composeTestRule.onNodeWithTag("PasswordField")
            .performClick()
            .performTextInput("password123")

        composeTestRule.onNodeWithTag("LoginButton").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("MainScreen").isDisplayed()
        composeTestRule.onNodeWithTag("LoginPage").isNotDisplayed()
    }
}
