package com.lushan.securedapp.ui.main

import app.cash.turbine.test
import com.lushan.securedapp.MainDispatcherRule
import com.lushan.securedapp.domain.model.SecureData
import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.domain.usecase.CheckEnvironmentSecurityUseCase
import com.lushan.securedapp.domain.usecase.GetSensitivityDataUseCase
import com.lushan.securedapp.ui.state.DataState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests for the [MainViewModel].
 *
 * This test class verifies the behavior of the MainViewModel, ensuring that it correctly
 * handles data fetching and environment security checks. It uses MockK for mocking dependencies
 * and Turbine for testing Kotlin Flows.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    // This rule swaps the Main dispatcher for a test dispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Create mocks for the ViewModel's dependencies
    private val getSensitivityDataUseCase: GetSensitivityDataUseCase = mockk()
    private val environmentSecurityUseCase: CheckEnvironmentSecurityUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    // Initialize with secure Environment Tests
    @Test
    fun `init WHEN security check is true THEN isEnvironmentSecure state is true`() {
        // The security check use case will return true
        every { environmentSecurityUseCase() } returns true

        // The ViewModel is initialized
        viewModel = MainViewModel(getSensitivityDataUseCase, environmentSecurityUseCase)

        // Verify that the isEnvironmentSecure state is true
        assertTrue(viewModel.isEnvironmentSecure.value!!)
    }

    // Initialize with unsecure Environment Tests
    @Test
    fun `init WHEN security check is false THEN isEnvironmentSecure state is false`() {
        // The security check use case will return false
        every { environmentSecurityUseCase() } returns false

        // The ViewModel is initialized
        viewModel = MainViewModel(getSensitivityDataUseCase, environmentSecurityUseCase)

        // Verify that the isEnvironmentSecure state is false
        assertFalse(viewModel.isEnvironmentSecure.value!!)
    }

    @Test
    fun `fetchSensitiveData WHEN use case succeeds THEN dataState emits Success`() = runTest {
        // The environment is secure
        every { environmentSecurityUseCase() } returns true

        val successData = SensitiveData(message = "Success!", data = SecureData(title = "Success Title", description = "Success Description"))
        val successFlow = flowOf(DataState.Success(successData))
        coEvery { getSensitivityDataUseCase() } returns successFlow

        // ViewModel is initialized
        viewModel = MainViewModel(getSensitivityDataUseCase, environmentSecurityUseCase)
        viewModel.fetchSensitiveData()

        viewModel.dataState.test {
            // Verify that the dataState emits Success
            assertEquals(DataState.Success(successData), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchSensitiveData WHEN use case fails THEN dataState emits Error` () = runTest {
        // The environment is secure
        every { environmentSecurityUseCase() } returns true
        val errorMessage = "403 Forbidden"

        // Returns the Error state
        val errorFlow = flowOf(DataState.Error(errorMessage))
        coEvery { getSensitivityDataUseCase() } returns errorFlow

        // ViewModel is initialized
        viewModel = MainViewModel(getSensitivityDataUseCase, environmentSecurityUseCase)
        viewModel.fetchSensitiveData()

        viewModel.dataState.test {
            // Verify that the dataState emits Error
            assertEquals(DataState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}