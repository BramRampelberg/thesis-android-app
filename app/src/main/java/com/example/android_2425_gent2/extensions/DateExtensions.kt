package com.example.android_2425_gent2.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun LocalDateTime.formatRelative(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a", Locale.getDefault())
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())

    val isToday = this.toLocalDate() == now.toLocalDate()
    val isYesterday = this.toLocalDate() == now.toLocalDate().minusDays(1)

    return when {
        isToday -> this.format(formatter)
        isYesterday -> "Yesterday ${this.format(formatter)}"
        isWithinLastWeek() -> "${this.format(dayFormatter)} ${this.format(formatter)}"
        else -> this.format(dateFormatter)
    }
}

private fun LocalDateTime.isWithinLastWeek(): Boolean {
    val now = LocalDateTime.now()
    val daysBetween = ChronoUnit.DAYS.between(this, now)
    return daysBetween in 0..7
}