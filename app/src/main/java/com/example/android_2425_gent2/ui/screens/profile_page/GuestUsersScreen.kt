package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.ui.AppViewModelProvider
import com.example.android_2425_gent2.ui.common.ErrorMessage
import com.example.android_2425_gent2.ui.screens.profile_page.partials.AppTopBar
import com.example.android_2425_gent2.ui.screens.profile_page.partials.GuestUsersList

@Composable
fun GuestUsersScreen(
    viewModel: GuestUsersViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigateToUserDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column() {
        AppTopBar(title = stringResource(R.string.users_list))

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
                GuestUsersList(
                    users = uiState.users,
                    onUserClick = onNavigateToUserDetails,
                    modifier = Modifier
                )
            }
        }
    }
}