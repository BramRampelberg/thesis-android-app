package com.example.android_2425_gent2.ui.screens.login_page

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginUITests {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()

        composeTestRule.setContent {
            Android2425gent2Theme {
                LoginPage()
            }
        }
    }

    @Test
    fun showsEmailField() {
        composeTestRule.onNodeWithTag("EmailField").assertExists()
    }

    @Test
    fun showsPasswordField() {
        composeTestRule.onNodeWithTag("PasswordField").assertExists()
    }

    @Test
    fun clickLoginButton_showsLoadingIndicator() {
        //Input email
        composeTestRule.onNodeWithTag("EmailField").performClick()
        composeTestRule.onNodeWithTag("EmailField")
            .performTextInput("example@email.com")

        //Input password
        composeTestRule.onNodeWithTag("PasswordField").performClick()
        composeTestRule.onNodeWithTag("PasswordField")
            .performTextInput("password123")

        //Click login button
        composeTestRule.onNodeWithTag("LoginButton").performClick()

        //Assert that the loading indicator exists
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()
    }

    @Test
    fun wrongEmailFormat_ShowsErrorMessage() {
        //Arrange
        composeTestRule.onNodeWithTag("EmailField").performClick()

        //Act
        composeTestRule.onNodeWithTag("EmailField")
            .performTextInput("exampleemailcom")

        //Assert
        composeTestRule.onNodeWithTag("ErrorText").assertExists()
        composeTestRule.onNodeWithTag("ErrorText")
            .assertTextEquals("Invalid email format")
    }

    @Test
    fun shortPassword_ShowsErrorMessage() {
        //Arrange
        composeTestRule.onNodeWithTag("PasswordField").performClick()

        //Act
        composeTestRule.onNodeWithTag("PasswordField")
            .performTextInput("1234567")

        //Assert
        composeTestRule.onNodeWithTag("ErrorText").assertExists()
        composeTestRule.onNodeWithTag("ErrorText")
            .assertTextEquals("Password must be at least 8 characters")
    }

    @Test
    fun longPassword_ShowsErrorMessage() {
        //Arrange
        composeTestRule.onNodeWithTag("PasswordField").performClick()

        //Act
        composeTestRule.onNodeWithTag("PasswordField")
            .performTextInput("123456789012345678901234567890123456789012345678901234567890123456789012")

        //Assert
        composeTestRule.onNodeWithTag("ErrorText").assertExists()
        composeTestRule.onNodeWithTag("ErrorText")
            .assertTextEquals("Password must be less than or equal to 72 characters")
    }
}