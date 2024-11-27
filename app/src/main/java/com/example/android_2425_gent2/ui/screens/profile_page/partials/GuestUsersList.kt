package com.example.android_2425_gent2.ui.screens.profile_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items


data class UserListItem(
    val id: String,
    val name: String,
    val createdDate: String
)

@Composable
fun GuestUsersList(
    modifier: Modifier = Modifier,
    onUserClick: (String) -> Unit
) {
    val users = listOf(
        UserListItem("1", "John Doe", "2024-03-15"),
        UserListItem("2", "Jane Smith", "2024-03-14"),
        UserListItem("3", "Bob Johnson", "2024-03-13")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                UserListItemCard(
                    user = user,
                    onClick = onUserClick,
                    modifier = modifier
                )
            }
        }
    }
}