package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.ui.screens.profile_page.partials.AppTopBar
import com.example.android_2425_gent2.ui.screens.profile_page.partials.GuestUsersList

@Composable
fun GuestUsersScreen(
    modifier: Modifier,
    onNavigateToUserDetails: (String) -> Unit,

    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.secondary))
    ) {
        AppTopBar(
            title = stringResource(R.string.users_list),
            modifier = modifier.fillMaxWidth()
        )

        GuestUsersList(
            onUserClick = onNavigateToUserDetails,
            modifier = Modifier.fillMaxWidth()
        )
    }
}