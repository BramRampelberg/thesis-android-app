package com.example.android_2425_gent2.ui.screens.reservations_page

import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasRequestFocusAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.data.model.Reservation
import com.example.android_2425_gent2.data.test_data.getTestReservations
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import com.example.android_2425_gent2.utils.TIME_FORMATTER
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReservationsUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer()
        composeTestRule.setContent {
            Android2425gent2Theme {
                ReservationsPage()
            }
        }
    }

    @Test
    fun selectOldReservations_showsOldReservations() {
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithText("Oude reservaties").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[1].timeSlot?.date}")
            .assertExists()
    }

    @Test
    fun selectUpcomingReservations_showsUpcomingReservations() {
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithTag("ReservationType.UPCOMING").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[2].timeSlot?.date}")
            .assertExists()
    }

    @Test
    fun selectCanceledReservations_showsCanceledReservations() {
        composeTestRule.onNodeWithTag("ReservationTypeSelectionDropDownMenu").performClick()
        composeTestRule.onNodeWithText("Geannuleerde reservaties").performClick()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[0].timeSlot?.date}")
            .assertExists()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[1].timeSlot?.date}")
            .assertExists()
        composeTestRule.onNodeWithText("Datum: ${getTestReservations()[2].timeSlot?.date}")
            .assertExists()
    }

    @Test
    fun selectReservation_showsReservationDetails() {
        val reservation: Reservation = getTestReservations()[2]
        composeTestRule.onNodeWithText("Datum", substring = true).performClick()
        composeTestRule.onAllNodesWithText("${reservation.timeSlot?.date}", substring = true)
            .filter(
                hasRequestFocusAction()
            ).onFirst().assertExists()
        composeTestRule.onAllNodesWithText(
            "${reservation.timeSlot?.start?.format(TIME_FORMATTER)} - ${
                reservation.timeSlot?.end?.format(
                    TIME_FORMATTER
                )
            }",
        ).filter(
            hasRequestFocusAction()
        ).onFirst().assertExists()
        composeTestRule.onAllNodesWithText("Boot: ${reservation.boat?.name}", substring = true)
            .filter(
                hasRequestFocusAction()
            ).onFirst().assertExists()
        composeTestRule.onNodeWithText("Gegevens ophalen batterij:").assertExists()
        composeTestRule.onNodeWithText("Naam", substring = true).assertExists()
        composeTestRule.onNodeWithText("Tel.:", substring = true).assertExists()
        composeTestRule.onNodeWithText("E-mail:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Annuleer reservatie").assertExists()
    }
}