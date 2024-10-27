package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.data.test_data.getTestReservations
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReservationsUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun selectOldReservations() {
        composeTestRule.setContent {
            Android2425gent2Theme {
                ReservationsPage()
            }
        }
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithText("Oude reservaties").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[1].timeSlot?.date}")
            .assertExists()
    }

    @Test
    fun selectUpcomingReservations() {
        composeTestRule.setContent {
            Android2425gent2Theme {
                ReservationsPage()
            }
        }
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithTag("ReservationType.UPCOMING").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[2].timeSlot?.date}")
            .assertExists()
    }

    @Test
    fun selectCanceledReservations() {
        composeTestRule.setContent {
            Android2425gent2Theme {
                ReservationsPage()
            }
        }
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithText("Geannuleerde reservaties").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[0].timeSlot?.date}")
            .assertExists()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[1].timeSlot?.date}")
            .assertExists()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[2].timeSlot?.date}")
            .assertExists()
    }
}