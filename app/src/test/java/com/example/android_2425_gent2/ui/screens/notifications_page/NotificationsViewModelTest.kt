package com.example.android_2425_gent2.ui.screens.notifications_page

import com.example.android_2425_gent2.data.model.Notification
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.notification.NotificationRepository
import com.example.android_2425_gent2.ui.screens.MainDispatcherRule
import com.example.android_2425_gent2.ui.screens.notification_page.NotificationViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {
    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val notificationRepository: NotificationRepository = mockk()
    private lateinit var viewModel: NotificationViewModel

    private val sampleNotification1 = Notification(
        id = 1,
        severity = 1,
        title = "Test Notification 1",
        message = "Test Message 1",
        timeStamp = Date(),
        isRead = false
    )

    private val sampleNotification2 = Notification(
        id = 2,
        severity = 2,
        title = "Test Notification 2",
        message = "Test Message 2",
        timeStamp = Date(),
        isRead = true
    )

    @Test
    fun `initial state should be loading`() = runTest {
        coEvery { notificationRepository.notifications } returns flow {
            emit(APIResource.Loading())
        }
        coEvery { notificationRepository.getNotifications() } returns flow {
            emit(APIResource.Loading())
        }

        viewModel = NotificationViewModel(notificationRepository)

        assertTrue(viewModel.notificationsUiState.value.loading)
        assertTrue(viewModel.notificationsUiState.value.notifications.isEmpty())
    }

    @Test
    fun `successful notifications load should update state correctly`() = runTest {
        val mockNotifications = listOf(sampleNotification1, sampleNotification2)

        coEvery { notificationRepository.notifications } returns flow {
            emit(APIResource.Success(mockNotifications))
        }
        coEvery { notificationRepository.getNotifications() } returns flow {
            emit(APIResource.Success(mockNotifications))
        }

        viewModel = NotificationViewModel(notificationRepository)
        advanceUntilIdle()

        with(viewModel.notificationsUiState.value) {
            assertFalse(loading)
            assertFalse(hasError)
            assertEquals(mockNotifications, notifications)
        }
    }

    @Test
    fun `error during notifications load should update error state`() = runTest {
        val errorMessage = "Network error"

        coEvery { notificationRepository.notifications } returns flow {
            emit(APIResource.Error(errorMessage))
        }
        coEvery { notificationRepository.getNotifications() } returns flow {
            emit(APIResource.Error(errorMessage))
        }

        viewModel = NotificationViewModel(notificationRepository)
        advanceUntilIdle()

        with(viewModel.notificationsUiState.value) {
            assertFalse(loading)
            assertTrue(hasError)
            assertEquals(errorMessage, errorMessage)
        }
    }

    @Test
    fun `unread count should update correctly when notifications change`() = runTest {
        val mockNotifications = listOf(
            sampleNotification1.copy(isRead = false),
            sampleNotification2.copy(isRead = false),
            sampleNotification1.copy(id = 3, isRead = true)
        )

        coEvery { notificationRepository.notifications } returns flow {
            emit(APIResource.Success(mockNotifications))
        }
        coEvery { notificationRepository.getNotifications() } returns flow {
            emit(APIResource.Success(mockNotifications))
        }

        viewModel = NotificationViewModel(notificationRepository)
        advanceUntilIdle()

        assertEquals(2, viewModel.unreadCount.value)
    }

    @Test
    fun `unread count should be zero when all notifications are read`() = runTest {
        val mockNotifications = listOf(
            sampleNotification1.copy(isRead = true),
            sampleNotification2.copy(isRead = true)
        )

        coEvery { notificationRepository.notifications } returns flow {
            emit(APIResource.Success(mockNotifications))
        }
        coEvery { notificationRepository.getNotifications() } returns flow {
            emit(APIResource.Success(mockNotifications))
        }

        viewModel = NotificationViewModel(notificationRepository)
        advanceUntilIdle()

        assertEquals(0, viewModel.unreadCount.value)
    }
}