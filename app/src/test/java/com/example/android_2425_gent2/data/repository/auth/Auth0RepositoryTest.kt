package com.example.android_2425_gent2.data.repository.auth

import android.util.Log
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.callback.Callback
import com.auth0.android.request.AuthenticationRequest
import com.auth0.android.result.Credentials
import com.example.android_2425_gent2.data.repository.APIResource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse


class Auth0RepoTest {
    private lateinit var auth0Repo: Auth0Repo
    private val mockAuthenticationAPIClient = mockk<AuthenticationAPIClient>(relaxed = true)
    private val mockCredentialsManager = mockk<SecureCredentialsManager>(relaxed = true)

    private val testCredentials = Credentials(
        idToken = "testIdToken",
        accessToken = "testAccessToken",
        type = "Bearer",
        scope = "openid profile email",
        expiresAt = mockk(),
        refreshToken = null
    )

    @Before
    fun setup() {
        auth0Repo = Auth0Repo(mockAuthenticationAPIClient, mockCredentialsManager)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.d(any(), any(), any()) } returns 0
        every { Log.i(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun getStoredCredentials_success_credentialsRetrievedSuccessfully() = runTest {
        coEvery { mockCredentialsManager.getCredentials(any()) } answers {
            val callback = it.invocation.args[0] as Callback<Credentials, CredentialsManagerException>
            callback.onSuccess(testCredentials)
        }

        val result = auth0Repo.getStoredCredentials().toList().last()

        assertTrue(result is APIResource.Success)
        val successResult = result as APIResource.Success
        assertEquals(testCredentials, successResult.data)
        
        coVerify(exactly = 1) { mockCredentialsManager.getCredentials(any()) }
    }

    @Test
    fun getStoredCredentials_failure_errorReturnedWhenFailureOccurs() = runTest {
        coEvery { mockCredentialsManager.getCredentials(any()) } answers {
            val callback = it.invocation.args[0] as Callback<Credentials, CredentialsManagerException>
            callback.onFailure(mockk())
        }
        
        val result = auth0Repo.getStoredCredentials().toList().last()

        assertTrue(result is APIResource.Error)
        val errorResult = result as APIResource.Error
        assertEquals("Error retrieving stored credentials: null", errorResult.message)
        
        coVerify(exactly = 1) { mockCredentialsManager.getCredentials(any()) }
    }

    @Test
    fun login_ValidLogin_RetrievesCredentials() = runTest {
        val userName = "testUser"
        val password = "testPassword"

        val mockRequest = mockk<AuthenticationRequest>(relaxed = true)

        coEvery { mockAuthenticationAPIClient.login(userName, password) } returns mockRequest

        every { mockRequest.setScope(any()) } returns mockRequest
        every { mockRequest.validateClaims() } returns mockRequest

        every { mockRequest.start(any()) } answers {
            val callback = it.invocation.args[0] as Callback<Credentials, AuthenticationException>
            callback.onSuccess(testCredentials)
        }

        coEvery { mockCredentialsManager.saveCredentials(testCredentials) } just Runs
        
        val result = auth0Repo.login(userName, password).toList().last()

        assertTrue(result is APIResource.Success)
        val successResult = result as APIResource.Success
        assertEquals(testCredentials, successResult.data)

        coVerify { mockCredentialsManager.saveCredentials(testCredentials) }
        coVerify { mockAuthenticationAPIClient.login(userName, password) }
    }

    @Test
    fun login_AuthenticationException_Error() = runTest {
        val userName = "testUser"
        val password = "testPassword"

        val mockRequest = mockk<AuthenticationRequest>(relaxed = true)
        coEvery { mockAuthenticationAPIClient.login(userName, password) } returns mockRequest

        every { mockRequest.setScope(any()) } returns mockRequest
        every { mockRequest.validateClaims() } returns mockRequest

        val mockAuthException = mockk<AuthenticationException> {
            every { getDescription() } returns "Invalid credentials"
        }
        every { mockRequest.start(any()) } answers {
            val callback = it.invocation.args[0] as Callback<Credentials, AuthenticationException>
            callback.onFailure(mockAuthException)
        }

        val result = auth0Repo.login(userName, password).first()

        assertTrue(result is APIResource.Error)
        val errorResult = result as APIResource.Error
        assertEquals("Authentication failed: Invalid credentials", errorResult.message)
    }


    @Test
    fun login_GeneralException_Error() = runTest {
        val userName = "testUser"
        val password = "testPassword"

        val mockRequest = mockk<AuthenticationRequest>(relaxed = true)
        coEvery { mockAuthenticationAPIClient.login(userName, password) } returns mockRequest

        every { mockRequest.setScope(any()) } returns mockRequest
        every { mockRequest.validateClaims() } returns mockRequest
        every { mockRequest.start(any()) } answers {
            throw Exception("Unexpected error")
        }

        val result = auth0Repo.login(userName, password).first()

        assertTrue(result is APIResource.Error)
        val errorResult = result as APIResource.Error
        assertEquals("An error occurred: Unexpected error", errorResult.message)
    }

    @Test
    fun logout_LogsOut_LoggedOut() {
        auth0Repo.logout()
        
        coVerify { mockCredentialsManager.clearCredentials() }
    }

    @Test
    fun isLoggedIn_LoggedIn_True() {
        every { mockCredentialsManager.hasValidCredentials() } returns true
        
        val result = auth0Repo.isLoggedIn()
        
        assertTrue(result)
    }

    @Test
    fun isLoggedIn_LoggedOut_False() {
        every { mockCredentialsManager.hasValidCredentials() } returns false
        
        val result = auth0Repo.isLoggedIn()
        
        assertFalse(result)
    }
}
