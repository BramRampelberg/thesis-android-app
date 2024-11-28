package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R
import com.example.android_2425_gent2.ui.screens.profile_page.partials.AppTopBar
import com.example.android_2425_gent2.ui.screens.profile_page.partials.ProfileActionCard
import com.example.android_2425_gent2.ui.screens.profile_page.partials.ProfileHeader
import com.example.android_2425_gent2.ui.screens.profile_page.partials.ProfileNavigationButton

@Composable
fun ProfilePage(
    onNavigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.secondary))
    ) {
        AppTopBar(title=stringResource(R.string.profile))

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(
                name = "John Doe",
                email = "john.doe@example.com"
            )

            Spacer(modifier = modifier.height(24.dp))

            val profileActions = listOf(
                ProfileAction(
                    icon = Icons.Default.Edit,
                    text = stringResource(R.string.edit_profile),
                    onClick = { /* TODO: handle this later*/ }
                ),
                ProfileAction(
                    icon = Icons.Default.Lock,
                    text = stringResource(R.string.privacy_settings),
                    onClick = { /* TODO: handle this later*/ }
                )
            )

            ProfileActionCard(actions = profileActions)

            Spacer(modifier = modifier.height(24.dp))

            ProfileNavigationButton(
                text = stringResource(R.string.go_to_dashboard),
                icon = Icons.Default.AccountBox,
                onClick = onNavigateToDashboard
            )
        }
    }
}

/*require this for a action in profile page*/
data class ProfileAction(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit
)