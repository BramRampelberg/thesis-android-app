package com.example.android_2425_gent2.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm")