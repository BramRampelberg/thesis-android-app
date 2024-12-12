package com.example.android_2425_gent2.ui.screens.profile_page

import com.example.android_2425_gent2.data.network.battery.BatteryDto
import com.example.android_2425_gent2.data.network.boat.BoatDto
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.battery.BatteryRepository
import com.example.android_2425_gent2.data.repository.boat.BoatRepository
import com.example.android_2425_gent2.ui.screens.reservations_page.coroutine.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BatteryManagementViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private lateinit var viewModel: BatteryManagementViewModel
    private val mockBatteryRepository: BatteryRepository = mockk()
    private val mockBoatRepository: BoatRepository = mockk()

    private val testBoats = listOf(
        BoatDto(
            id = 1, 
            name = "Test Boat 1",
            personalName = "Limba",
            isAvailable = true
        ),
        BoatDto(
            id = 2, 
            name = "Test Boat 2",
            personalName = "Leith",
            isAvailable = true
        )
    )

    private val testBatteries = listOf(
        BatteryDto(id = 1, mentorId = null, mentorName = null),
        BatteryDto(id = 2, mentorId = 1, mentorName = "Test Mentor")
    )

    @Before
    fun setup() {
        coEvery { mockBoatRepository.getBoats() } returns flow {
            emit(APIResource.Success(testBoats))
        }
        
        coEvery { mockBatteryRepository.getBatteriesByBoat(any()) } returns flow {
            emit(APIResource.Success(testBatteries))
        }

        viewModel = BatteryManagementViewModel(mockBatteryRepository, mockBoatRepository)
    }

    @Test
    fun `initialization loads boats successfully`() = runTest {
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertFalse(isLoading)
            assertEquals(testBoats, boats)
            assertTrue(errorMessage.isEmpty())
        }

        coVerify { mockBoatRepository.getBoats() }
    }

    @Test
    fun `selecting boat loads batteries`() = runTest {
        val boatId = 1
        
        viewModel.selectBoat(boatId)
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertFalse(isLoading)
            assertEquals(testBatteries, batteries)
            assertEquals(boatId, selectedBoatId)
            assertTrue(errorMessage.isEmpty())
        }

        coVerify { mockBatteryRepository.getBatteriesByBoat(boatId) }
    }

    @Test
    fun `assign mentor updates battery successfully`() = runTest {
        val batteryId = 1
        val mentorId = 2

        coEvery { mockBatteryRepository.assignMentor(any(), any()) } returns flow {
            emit(APIResource.Success(Unit))
        }

        viewModel.assignMentor(batteryId, mentorId)
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertFalse(isLoading)
            assertTrue(errorMessage.isEmpty())
            assertEquals("Mentor successfully assigned", successMessage)
        }

        coVerify { mockBatteryRepository.assignMentor(batteryId, mentorId) }
    }

    @Test
    fun `error during boat loading shows error message`() = runTest {
        val errorMessage = "Failed to load boats"
        
        coEvery { mockBoatRepository.getBoats() } returns flow {
            emit(APIResource.Error(errorMessage))
        }

        viewModel = BatteryManagementViewModel(mockBatteryRepository, mockBoatRepository)
        advanceUntilIdle()

        with(viewModel.uiState.value) {
            assertFalse(isLoading)
            assertTrue(boats.isEmpty())
            assertEquals(errorMessage, this.errorMessage)
        }
    }
} 