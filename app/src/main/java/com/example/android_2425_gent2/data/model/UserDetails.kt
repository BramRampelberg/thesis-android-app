package com.example.android_2425_gent2.data.model

import com.example.android_2425_gent2.data.network.model.UserDetailsDto

data class UserDetails(
    val firstName: String,
    val email: String,
    val familyName: String,
) {
    companion object {
        fun fromDto(dto: UserDetailsDto): UserDetails = UserDetails(
            dto.firstName,
            dto.email,
            dto.familyName
        )
    }
}
