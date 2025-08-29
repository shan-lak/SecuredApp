package com.lushan.securedapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit Test Rule that sets the main dispatcher to a [TestDispatcher] for unit testing.
 *
 * This rule allows tests to run coroutines that use the Main dispatcher without needing
 * Android dependencies. It sets the main dispatcher to the provided [testDispatcher]
 * before each test and resets it after the test completes.
 *
 * @param testDispatcher The [TestDispatcher] to set as the main dispatcher. Defaults to [UnconfinedTestDispatcher].
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
