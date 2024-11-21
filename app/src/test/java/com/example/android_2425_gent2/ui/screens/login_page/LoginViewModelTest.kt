package com.example.android_2425_gent2.ui.screens.login_page

import com.auth0.android.result.Credentials
import com.example.android_2425_gent2.BuildConfig
import com.example.android_2425_gent2.data.repository.APIResource
import com.example.android_2425_gent2.data.repository.auth.IAuthRepo
import com.example.android_2425_gent2.ui.screens.MainDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.whenever
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val credentials: Credentials = Credentials(
        idToken =  "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlU4Rkw5Q1pyWkxJRjdHek9yQVZDWSJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOltdLCJuaWNrbmFtZSI6ImFkbWluIiwibmFtZSI6ImFkbWluQGFkbWluLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci82NGUxYjhkMzRmNDI1ZDE5ZTFlZTJlYTcyMzZkMzAyOD9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmFkLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDI0LTExLTE4VDE1OjI3OjQ2LjI0NFoiLCJlbWFpbCI6ImFkbWluQGFkbWluLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiaXNzIjoiaHR0cHM6Ly9yaXNlLWdlbnQyLmV1LmF1dGgwLmNvbS8iLCJhdWQiOiI4dkp0YlhnMkZwdEhHbUtycEZsMXRad2hpWE9KWjU3bCIsImlhdCI6MTczMTk0MzY2NywiZXhwIjoxNzMxOTc5NjY3LCJzdWIiOiJhdXRoMHw2NzJiNzVjYjRjYTU0MjhmMzVkZDVhNmQifQ.H5MMUJovGHoAkhvu0F3gV4ehMwOrPsHc6_r6GG-yVSS6i-Pq5geSzILXyafFhqa-SCIbPHWmlk3S5Tt5dMXC8gxhmueEXy28LC9jTxngiPePKi9YlQu3l4cb2VkGN_CfVs-tMIVPb_MB4GGk850sq1Ihw9sNzRbOC6-4PhyRB3V4XrpRkPPgZ4HDWBOyuGxralpfelEC2epSk4ID168HbrLruwvg62kOkyWh7sg5XjKUv2__ZplkUpukbDcvWM8Zo-p5GQnn4EQh48Jmyv9urPZMv0-G3KrXo3NlKnZdOFkFK8Ch5EV1BVYVkQM56OfPTjQ2vTEQq_IH-Sr1X3jArg",
        accessToken = "eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIiwiaXNzIjoiaHR0cHM6Ly9yaXNlLWdlbnQyLmV1LmF1dGgwLmNvbS8ifQ..S8Zc-zNWh4Dg_P0e.vt8Lgwlt-cvILfFIQhe2-5s6dZov-gTrSPiiXg4BL4JfdxjQlpVlh42JJkUIeEJ7spmyxrcGmSeJzxobXBeSIc12UQamFprRkwu7YdgwNHOXG6GAcN32XpUbzEjRMD4C5xmFsFNijoSbDmF1bRdMDRaYZqk33sTipKLksWgikV1cF0vvP4OailswsgwWuvFsLddKAnFkO6iQ-wH3JoQc-CoHDdRMSzM-R0spcAF3257QKxbBqE8hG8vH3P7KCowem0WmAnBzXo0ByD_40r4BNwttaBWhpv-VQgLU85V_Gz3Ce07sEB1LS3FlrMwKoaabp2SicklfXvyXZDVhE7TbWhz7Yd3C0inkwk67j0f-Xs7-T5fAid4v-PBoZwyUhSF_gzinfiz3Z8nVzm7ufpYyZpZ6U0MUaft28NYnYmMTZUJ0.I4CbqVPKqxBNSyxfQ8vFnw",
        type = "Bearer",
        scope = "openid profile email",
        expiresAt = Date.from(LocalDateTime.now().plus(Duration.ofSeconds(86400)).atZone(ZoneId.systemDefault()).toInstant()),
        refreshToken = null,
    )

    private val email: String = "test@example.com"
    private val password: String = "password123"

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel
    private val mockAuthRepo: IAuthRepo = mock()
    private val mockLogin: (Credentials) -> Unit = mock()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(mockLogin, mockAuthRepo)
    }

    @Test
    fun login_ValidEmailAndPassword_EnableLoginButton() {
        viewModel.setEmail("test@example.com")
        viewModel.setPassword("password123")

        viewModel.onAnyInputChanged()

        assertFalse(viewModel.uiState.disableLogin)
        assertNull(viewModel.uiState.error)
    }

    @Test
    fun setEmail_InvalidEmail_ShowError() {
        viewModel.setEmail("invalid-email")
        viewModel.onAnyInputChanged()

        assertEquals("Invalid email format", viewModel.uiState.error)
        assertTrue(viewModel.uiState.disableLogin)
    }

    @Test
    fun setPassword_InvalidPassword_ShowError() {
        viewModel.setPassword("")
        viewModel.onAnyInputChanged()

        assertEquals("Password is required", viewModel.uiState.error)
        assertTrue(viewModel.uiState.disableLogin)
    }



    @Test
    fun handleRegistration_Valid_OpenUrlChanged() {
        viewModel.handleRegister()

        assertEquals(BuildConfig.REGISTRATION_URL, viewModel.openUrlEvent.value)
    }

    @Test
    fun handleRegistration_Valid_RedirectToUrl() {
        viewModel.handleRegister()

        viewModel.onUrlOpened()

        assertNull(viewModel.openUrlEvent.value)
    }

    @Test
    fun login_ValidLogin_TriggersCallback() = runTest {
        viewModel.setEmail(email)
        viewModel.setPassword(password)
        viewModel.onAnyInputChanged()

        whenever(mockAuthRepo.login(email, password))
            .thenReturn(flow { emit(APIResource.Success(credentials)) })

        viewModel.handleLogin()

        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertEquals(false, uiState.disableLogin)
        assertNull(uiState.error)
        assertEquals(false, uiState.isLoading)

        verify(mockAuthRepo).login(email, password)
        verify(mockLogin).invoke(credentials)
    }

    @Test
    fun login_InvalidLogin_ShowError() = runTest {
        val errorMessage = "Invalid credentials"
        viewModel.setEmail(email)
        viewModel.setPassword(password)
        viewModel.onAnyInputChanged()

        whenever(mockAuthRepo.login(email, password))
            .thenReturn(flow { emit(APIResource.Error(errorMessage)) })

        viewModel.handleLogin()

        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertEquals(false, uiState.disableLogin)
        assertEquals(errorMessage, viewModel.uiState.error)
        assertEquals(false, uiState.isLoading)

        verify(mockAuthRepo).login(email, password)
        verify(mockLogin, times(0)).invoke(credentials)
    }

    @Test
    fun login_SlowLoad_IsLoading() = runTest {
        viewModel.setEmail(email)
        viewModel.setPassword(password)

        whenever(mockAuthRepo.login(email, password))
            .thenReturn(flow { emit(APIResource.Loading())})

        viewModel.handleLogin()

        advanceUntilIdle()

        val uiState = viewModel.uiState

        assertTrue(uiState.isLoading)
    }
}
