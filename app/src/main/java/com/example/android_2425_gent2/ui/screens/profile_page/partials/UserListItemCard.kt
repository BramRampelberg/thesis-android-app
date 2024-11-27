package com.example.android_2425_gent2.ui.screens.profile_page.partials

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.R

@Composable
fun UserListItemCard(
    user: UserListItem,
    onClick: (String) -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            ,
        onClick = { onClick(user.id) },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.secondary_darker)
        )
    ) {
        Row(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.secondary_contrast_text),
                )
                Text(
                    text = "Created: ${user.createdDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.secondary_contrast_text),
                )
            }
        }
    }
}