package com.lushan.securedapp.domain.usecase

import com.lushan.securedapp.domain.manager.EnvironmentSecurityManager
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [CheckEnvironmentSecurityUseCase].
 *
 * This test class verifies the behavior of the [CheckEnvironmentSecurityUseCase] by mocking
 * the [EnvironmentSecurityManager] dependency. It checks both scenarios where the environment
 * is secure and not secure.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class CheckEnvironmentSecurityUseCaseTest {

    // Mock dependency creation
    private val environmentSecurityManager: EnvironmentSecurityManager = mockk()
    private lateinit var checkEnvironmentSecurityUseCase: CheckEnvironmentSecurityUseCase

    // Initialize the use case before each test
    @Before
    fun setUp() {
        checkEnvironmentSecurityUseCase = CheckEnvironmentSecurityUseCase(environmentSecurityManager)
    }

    // Secured environment
    @Test
    fun `invoke WHEN manager returns true THEN returns true`() {
        // Mock manager to report that the environment is secure.
        every { environmentSecurityManager.isEnvironmentSecure() } returns true

        val isSecure = checkEnvironmentSecurityUseCase()
        // Assert that the result is true.
        assertTrue(isSecure)
    }

    // Unsecured environment
    @Test
    fun `invoke WHEN manager returns false THEN returns false`() {
        // Mock manager to report that the environment is NOT secure.
        every { environmentSecurityManager.isEnvironmentSecure() } returns false

        val isSecure = checkEnvironmentSecurityUseCase()
        // Assert that the result is false.
        assertFalse(isSecure)
    }
}