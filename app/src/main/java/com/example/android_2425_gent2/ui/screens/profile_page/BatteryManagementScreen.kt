package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.android_2425_gent2.data.network.battery.BatteryDto
import com.example.android_2425_gent2.data.network.users.UserNameDto
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatteriesList(
    batteries: List<BatteryDto>,
    users: List<UserNameDto>,
    onAssignMentor: (Int, Int) -> Unit
) {
    LazyColumn {
        items(batteries) { battery ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Battery ID: ${battery.id}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Text(
                        text = "Type: ${battery.type}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Text(
                        text = "Current Mentor: ${battery.mentor?.fullName ?: "No mentor assigned"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    var expanded by remember { mutableStateOf(false) }
                    var selectedUser by remember { mutableStateOf<UserNameDto?>(null) }
                    
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = selectedUser?.fullName ?: "Select Mentor",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.heightIn(max = 300.dp)
                        ) {
                            users.take(8).forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(text = user.fullName) },
                                    onClick = {
                                        selectedUser = user
                                        expanded = false
                                        onAssignMentor(battery.id, user.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}