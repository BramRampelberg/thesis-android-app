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

    override suspend fun login(userName: String, password: String): Flow<APIResource<Credentials>> = flow {
        try {
            val credentials = withContext(Dispatchers.IO) {
                suspendCoroutine { continuation ->
                    authentication.login(userName, password)
                        .setScope("openid profile email")
                        .validateClaims()
                        .start(object : Callback<Credentials, AuthenticationException> {
                            override fun onSuccess(result: Credentials) {
                                continuation.resume(result)
                            }

                            override fun onFailure(error: AuthenticationException) {
                                continuation.resumeWithException(error)
                            }
                        })
                }
            }
            credentialsManager.saveCredentials(credentials)
            emit(APIResource.Success(credentials))

        } catch (e: AuthenticationException) {
            Log.e("Auth0Error", "Authentication failed: ${e.getDescription()}")
            emit(APIResource.Error("Authentication failed: ${e.getDescription()}"))
        } catch (e: Exception) {
            Log.e("LoginError", "Login failed: ${e.localizedMessage}", e)
            emit(APIResource.Error("An error occurred: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    fun logout() {
        credentialsManager.clearCredentials()
    }
}

