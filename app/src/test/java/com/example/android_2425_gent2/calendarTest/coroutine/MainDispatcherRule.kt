package com.example.android_2425_gent2.calendarTest.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.mockito.internal.verification.Description

@ExperimentalCoroutinesApi
class MainDispatcherRule : TestWatcher() {
    override fun starting(description: org.junit.runner.Description?) {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    override fun finished(description: org.junit.runner.Description?) {
        Dispatchers.resetMain()
    }
}
