package com.example.android_2425_gent2.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.android_2425_gent2.TestApplication

class TestInstrumentationRunner : AndroidJUnitRunner() {
    @Override
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, TestApplication::class.qualifiedName, context)
    }
}