package com.lushan.securedapp.domain.usecase

import app.cash.turbine.test
import com.lushan.securedapp.domain.model.SecureData
import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.domain.repository.SecurityRepository
import com.lushan.securedapp.ui.state.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test class for [GetSensitivityDataUseCase].
 *
 * This class contains unit tests to verify the behavior of the [GetSensitivityDataUseCase]
 * in different scenarios, including successful data retrieval and error handling.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class GetSensitiveDataUseCaseTest {

    // Mock dependency creation
    private val repository: SecurityRepository = mockk()
    private lateinit var getSensitiveDataUseCase: GetSensitivityDataUseCase

    // Initialize the use case before each test
    @Before
    fun setUp(){
        getSensitiveDataUseCase = GetSensitivityDataUseCase(repository)
    }

    // Success case
    @Test
    fun `invoke WHEN repository returns success THEN returns the success flow`() = runTest {
        // Sample data to return from the repository
        val samplePayload = SecureData(title = "Test Title", description = "Test Description")
        val sampleData = SensitiveData(message = "Success", data = samplePayload)
        val successFlow = flowOf(DataState.Success(sampleData))

        // When repository.getSensitiveData() is called, return successFlow
        coEvery { repository.getSensitiveData() } returns successFlow

        val result = getSensitiveDataUseCase()

        result.test {
            val emissionItem = awaitItem()
            // Verify it's a Success state
            assertTrue(emissionItem is DataState.Success)
            // Verify the data inside
            assertEquals(emissionItem.data , sampleData)
            awaitComplete()
        }
    }

    // Error case
    @Test
    fun `invoke WHEN repository returns error THEN returns the error flow`() = runTest {
        // Error message to return from the repository
        val errorMessage = "Error message"
        val errorFlow = flowOf(DataState.Error(errorMessage))

        // When repository.getSensitiveData() is called, return our errorFlow
        coEvery { repository.getSensitiveData() } returns errorFlow

        val result = getSensitiveDataUseCase()

        result.test {
            val emissionItem = awaitItem()
            // Verify it's an Error state
            assertTrue(emissionItem is DataState.Error)
            // Verify the message inside
            assertEquals(emissionItem.message, errorMessage)
            awaitComplete()
        }
    }
}