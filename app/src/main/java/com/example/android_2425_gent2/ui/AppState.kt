package com.example.android_2425_gent2.ui

import androidx.compose.runtime.saveable.Saver

data class AppState(var isLoggedIn: Boolean = false)
val AppStateSaver= Saver<AppState, Boolean>(
    save = { it.isLoggedIn },
    restore = { AppState(it) }

)