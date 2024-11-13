package com.example.android_2425_gent2.data.repository.auth

import android.util.Log
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.example.android_2425_gent2.data.repository.APIResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Auth0Repo(
    private val authentication: AuthenticationAPIClient,
    private val credentialsManager: SecureCredentialsManager
) : IAuthRepo {

    override suspend fun getStoredCredentials(): Flow<APIResource<Credentials>> = flow {
        emit(APIResource.Loading())

        try {
            // Retrieve or refresh credentials via SecureCredentialsManager
            val credentials = withContext(Dispatchers.IO) {
                suspendCoroutine { continuation ->
                    credentialsManager.getCredentials(object : Callback<Credentials, CredentialsManagerException> {
                        override fun onSuccess(result: Credentials) {
                            continuation.resume(result)
                        }

                        override fun onFailure(error: CredentialsManagerException) {
                            continuation.resumeWithException(error)
                        }
                    })
                }
            }

            emit(APIResource.Success(credentials))
        } catch (e: CredentialsManagerException) {
            emit(APIResource.Error("Error retrieving stored credentials: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    // Optionally provide a login method if login is not handled elsewhere in the app
    override suspend fun login(userName: String, password: String): Flow<APIResource<Credentials>> = flow {
        try {
            // Convert the Auth0 login callback to a suspending function using suspendCoroutine
            val credentials = withContext(Dispatchers.IO) {
                suspendCoroutine<Credentials> { continuation ->
                    authentication.login(userName, password)
                        .setScope("openid profile email")
                        .validateClaims()
                        .start(object : Callback<Credentials, AuthenticationException> {
                            override fun onSuccess(result: Credentials) {
                                // Resume the coroutine with the result on success
                                continuation.resume(result)
                            }

                            override fun onFailure(error: AuthenticationException) {
                                // Resume the coroutine with an exception on failure
                                continuation.resumeWithException(error)
                            }
                        })
                }
            }
            // Emit success once credentials are retrieved
            credentialsManager.saveCredentials(credentials)
            emit(APIResource.Success(credentials))

        } catch (e: AuthenticationException) {
            // Handle authentication error
            Log.e("Auth0Error", "Authentication failed: ${e.localizedMessage}")
            emit(APIResource.Error("Authentication failed: ${e.localizedMessage}"))
        } catch (e: Exception) {
            // Catch other potential errors
            Log.e("LoginError", "Login failed: ${e.localizedMessage}", e)
            emit(APIResource.Error("An error occurred: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    // Clear credentials on logout
    fun logout() {
        credentialsManager.clearCredentials()
    }
}

