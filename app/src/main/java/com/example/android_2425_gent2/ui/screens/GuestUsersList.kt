package com.example.android_2425_gent2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun GuestUsersList() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Users List",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Here you can view and manage all users",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}