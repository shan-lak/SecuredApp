package com.lushan.securedapp.domain.manager

/**
 * Interface to manage and verify the security of the application environment.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
interface EnvironmentSecurityManager {
    fun isEnvironmentSecure(): Boolean
}