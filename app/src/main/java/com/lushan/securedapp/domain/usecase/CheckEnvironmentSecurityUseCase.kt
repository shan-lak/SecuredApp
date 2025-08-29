package com.lushan.securedapp.domain.usecase

import com.lushan.securedapp.domain.manager.EnvironmentSecurityManager
import javax.inject.Inject

/**
 * Use case to check if the application environment is secure.
 * This includes checks for rooting, debugging, and other potential security threats.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class CheckEnvironmentSecurityUseCase @Inject constructor(
    private val environmentSecurityManager: EnvironmentSecurityManager
) {
    operator fun invoke(): Boolean{
        return environmentSecurityManager.isEnvironmentSecure()
    }
}