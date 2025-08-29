package com.lushan.securedapp.di

import com.lushan.securedapp.data.manager.EnvironmentSecurityManagerImpl
import com.lushan.securedapp.domain.manager.EnvironmentSecurityManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides bindings for security-related dependencies.
 *
 * This module is installed in the [SingletonComponent], making the provided dependencies
 * available throughout the entire application lifecycle.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {
    /**
     * Binds the [EnvironmentSecurityManagerImpl] implementation to the [EnvironmentSecurityManager] interface.
     *
     * @param impl The [EnvironmentSecurityManagerImpl] instance to be used.
     * @return The bound [EnvironmentSecurityManager] instance.
     */
    @Binds
    @Singleton
    abstract fun bindEnvironmentSecurityManager(impl: EnvironmentSecurityManagerImpl): EnvironmentSecurityManager
}
