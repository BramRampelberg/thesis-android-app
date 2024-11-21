package com.example.android_2425_gent2.ui.screens.app_page;

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.data.repository.auth.TestAuth0Repo
import com.example.android_2425_gent2.di.AppContainer
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO: refactor so that getting container and test repos is centralized
    private fun getContainer(): AppContainer {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        return application.container
    }

    private fun getTestAuth0Repo(): TestAuth0Repo {
        return getContainer().authRepo as TestAuth0Repo
    }

    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()

        composeTestRule.setContent {
            Android2425gent2Theme {
                App()
            }
        }
    }

    @Test
    fun showsLoginPageWhenNotLoggedIn() {
        composeTestRule.onNodeWithTag("LoginPage").assertExists()
        composeTestRule.onNodeWithTag("LoginPage").isDisplayed()
        composeTestRule.onNodeWithTag("MainScreen").assertDoesNotExist()
    }

    @Test
    fun showsMainScreenWhenLoggedIn() {
        val testAuth0Repo: TestAuth0Repo = getTestAuth0Repo()
        testAuth0Repo.login()
        composeTestRule.onNodeWithTag("MainScreen").assertExists()
        composeTestRule.onNodeWithTag("MainScreen").isDisplayed()
        composeTestRule.onNodeWithTag("LoginPage").assertDoesNotExist()
    }
}
