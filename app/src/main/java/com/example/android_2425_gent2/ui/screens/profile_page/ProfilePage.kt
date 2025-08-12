package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.common.ErrorMessage
import com.example.android_2425_gent2.ui.screens.profile_page.partials.LogOutButton
import com.example.android_2425_gent2.ui.screens.profile_page.partials.ProfileHeader

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    logout: () -> Unit
) {

    val viewModel: ProfilePageViewModel = viewModel(
        factory = AppViewModelProvider.Factory,
    )

    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            uiState.errorMessage.isNotEmpty() -> {
                ErrorMessage(
                    message = uiState.errorMessage,
                )
            }

            else -> {
                ProfileHeader(
                    name = uiState.user.firstName + " " + uiState.user.familyName,
                    email = uiState.user.email
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        LogOutButton(
            onClick = {
                viewModel.handleLogout {
                    logout()
                }
            }
        )
    }
}
