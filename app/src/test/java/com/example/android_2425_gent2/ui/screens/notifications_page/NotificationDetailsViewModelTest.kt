package com.example.android_2425_gent2.ui.screens.notifications_page

import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.notification.NotificationRepository
import com.example.android_2425_gent2.ui.screens.MainDispatcherRule
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationDetailsViewModelTest {
    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val notificationRepository: NotificationRepository = mockk()
    private lateinit var viewModel: NotificationDetailsViewModel

    private val sampleNotification = Notification(
        id = 1,
        severity = 1,
        title = "Test Notification",
        message = "Test Message",
        timeStamp = Date(),
        isRead = false
    )

    @Test
    fun `initial state should be empty`() = runTest {
        viewModel = NotificationDetailsViewModel(notificationRepository)

        assertNull(viewModel.notificationDetailsUiState.value.notification)
        assertFalse(viewModel.notificationDetailsUiState.value.isLoading)
        assertFalse(viewModel.notificationDetailsUiState.value.hasError)
        assertNull(viewModel.notificationDetailsUiState.value.errorMessage)
    }

    @Test
    fun `setNotification should update state and mark as read`() = runTest {
        coEvery { notificationRepository.markNotificationAsRead(1) } returns flow {
            emit(APIResource.Success(Unit))
        }

        viewModel = NotificationDetailsViewModel(notificationRepository)
        viewModel.setNotification(sampleNotification)
        advanceUntilIdle()

        assertEquals(sampleNotification, viewModel.notificationDetailsUiState.value.notification)
        coVerify { notificationRepository.markNotificationAsRead(1) }
    }

    @Test
    fun `markAsRead failure should update error state`() = runTest {
        val errorMessage = "Failed to mark as read"

        coEvery { notificationRepository.markNotificationAsRead(1) } returns flow {
            emit(APIResource.Error(errorMessage))
        }

        viewModel = NotificationDetailsViewModel(notificationRepository)
        viewModel.setNotification(sampleNotification)
        advanceUntilIdle()

        assertEquals(sampleNotification, viewModel.notificationDetailsUiState.value.notification)
        assertTrue(viewModel.notificationDetailsUiState.value.hasError)
        assertEquals(errorMessage, viewModel.notificationDetailsUiState.value.errorMessage)
    }
}