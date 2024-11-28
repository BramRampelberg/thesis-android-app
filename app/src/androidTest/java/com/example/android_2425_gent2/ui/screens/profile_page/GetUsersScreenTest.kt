//package com.example.android_2425_gent2.ui.screens.profile_page
//
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.performClick
//import androidx.test.core.app.ApplicationProvider
//import com.example.android_2425_gent2.MainApplication
//import com.example.android_2425_gent2.di.TestContainer
//import com.example.android_2425_gent2.ui.theme.Android2425gent2Theme
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.kotlin.mock
//import kotlinx.coroutines.flow.MutableStateFlow
//
//class GuestUsersScreenTest {
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    private lateinit var viewModel: GuestUsersViewModel
//    private val mockNavigateToUserDetails: (String) -> Unit = mock()
//    private val mockNavigateBack: () -> Unit = mock()
//
//    @Before
//    fun setUp() {
//        val application = ApplicationProvider.getApplicationContext() as MainApplication
//        application.container = TestContainer()
//
//        viewModel = mock()
//    }
//
//    @Test
//    fun showLoadingState() {
//        val uiState = GuestUsersUiState(isLoading = true)
//        setContent(uiState)
//
//        composeTestRule.onNodeWithTag("loading_indicator")
//            .assertExists()
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun showErrorState() {
//        val errorMessage = "Error loading users"
//        val uiState = GuestUsersUiState(errorMessage = errorMessage)
//        setContent(uiState)
//
//        composeTestRule.onNodeWithText(errorMessage)
//            .assertExists()
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun showUsersList() {
//        val users = listOf(
//            GuestUser("1", "John Doe", "john@example.com"),
//            GuestUser("2", "Jane Smith", "jane@example.com")
//        )
//        val uiState = GuestUsersUiState(users = users)
//        setContent(uiState)
//
//        // Verify each user is displayed
//        users.forEach { user ->
//            composeTestRule.onNodeWithText(user.name)
//                .assertExists()
//                .assertIsDisplayed()
//        }
//    }
//
//    @Test
//    fun clickUser_navigatesToUserDetails() {
//        val users = listOf(
//            GuestUser("1", "John Doe", "john@example.com")
//        )
//        val uiState = GuestUsersUiState(users = users)
//        setContent(uiState)
//
//        composeTestRule.onNodeWithText("John Doe")
//            .performClick()
//
//        verify(mockNavigateToUserDetails).invoke("1")
//    }
//
//    @Test
//    fun clickBackButton_navigatesBack() {
//        val uiState = GuestUsersUiState()
//        setContent(uiState)
//
//        composeTestRule.onNodeWithTag("back_button")
//            .performClick()
//
//        verify(mockNavigateBack).invoke()
//    }
//
//    private fun setContent(uiState: GuestUsersUiState) {
//        // Setup mock ViewModel state
//        whenever(viewModel.uiState).thenReturn(MutableStateFlow(uiState))
//
//        composeTestRule.setContent {
//            Android2425gent2Theme {
//                GuestUsersScreen(
//                    viewModel = viewModel,
//                    onNavigateToUserDetails = mockNavigateToUserDetails,
//                    onNavigateBack = mockNavigateBack
//                )
//            }
//        }
//    }
//}