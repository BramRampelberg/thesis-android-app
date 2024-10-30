package com.example.android_2425_gent2.ui.screens.calendar_page.testData

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android_2425_gent2.MainActivity
import com.example.android_2425_gent2.MainApplication
import com.example.android_2425_gent2.data.network.ApiService
import com.example.android_2425_gent2.data.remote.model.DayInfo
import com.example.android_2425_gent2.data.remote.model.TimeSlot
import com.example.android_2425_gent2.data.remote.model.TimeSlotResponse
import com.example.android_2425_gent2.data.repository.timeslot.TimeSlotRepository
import com.example.android_2425_gent2.di.TestContainer
import com.example.android_2425_gent2.ui.screens.calendar_page.CalendarView
import com.example.android_2425_gent2.ui.screens.reservations_page.ReservationsPage
import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class CalendarViewInstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()



    @Before
    fun setContainer() {
        val application = ApplicationProvider.getApplicationContext() as MainApplication
        application.container = TestContainer(ApplicationProvider.getApplicationContext())
        composeTestRule.setContent {
            Android2425gent2Theme {
                CalendarView()
            }
        }
    }
    @Test
    fun selectFullyBookedDay_doesNotShowTimeSlots() {

        composeTestRule.onNodeWithText("2").performClick()


        composeTestRule.onNodeWithText("Start: 09:00").assertDoesNotExist()
        composeTestRule.onNodeWithText("End: 11:30").assertDoesNotExist()
    }
    @Test
    fun selectAvailableDay_showsTimeSlots() {

        composeTestRule.onNodeWithText("1").performClick()


        composeTestRule.onNodeWithText("1", useUnmergedTree = true).assertExists()


        composeTestRule.onNodeWithText("Start: 09:00").assertExists()
        composeTestRule.onNodeWithText("End: 11:30").assertExists()
    }
    @Test
    fun selectTimeSlot_displaysReservationDetails() {

        composeTestRule.onNodeWithText("1").performClick()


        composeTestRule.onNodeWithText("Start: 09:00").performClick()


        composeTestRule.onNodeWithText("Reservatie details").assertExists()
        composeTestRule.onNodeWithText("Start Time: 09:00").assertExists()
        composeTestRule.onNodeWithText("End Time: 11:30").assertExists()
        composeTestRule.onNodeWithText("Reserveer").assertExists()
    }

}





