package com.example.android_2425_gent2.ui.screens.profile_page

import androidx.lifecycle.ViewModel
import com.example.android_2425_gent2.data.repository.auth.IAuthRepo

class ProfilePageViewModel(
    private val authRepo: IAuthRepo
): ViewModel() {
    fun handleLogout() {
        authRepo.logout()
    }
}