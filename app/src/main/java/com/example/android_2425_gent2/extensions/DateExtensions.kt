package com.example.android_2425_gent2.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.formatRelative(): String {
    val now = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply { time = this@formatRelative }

    val isToday = now.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)
            && now.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

    val isYesterday = now.get(Calendar.DAY_OF_YEAR) - dateCalendar.get(Calendar.DAY_OF_YEAR) == 1
            && now.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

    return when {
        isToday -> timeFormat.format(this)
        isYesterday -> "Yesterday ${timeFormat.format(this)}"
        isWithinLastWeek() -> "${dayFormat.format(this)} ${timeFormat.format(this)}"
        else -> dateFormat.format(this)
    }
}

private fun Date.isWithinLastWeek(): Boolean {
    val now = Calendar.getInstance()
    val week = Calendar.getInstance().apply {
        time = this@isWithinLastWeek
        add(Calendar.DAY_OF_YEAR, 7)
    }
    return now.before(week)
}