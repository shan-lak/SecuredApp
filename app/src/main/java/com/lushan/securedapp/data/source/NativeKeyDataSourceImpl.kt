package com.lushan.securedapp.data.source

import com.lushan.securedapp.domain.model.NativeKeyDataSource
import com.lushan.securedapp.core.security.Keys

/**
 * Implementation of [NativeKeyDataSource] that retrieves the secret key using native methods from [Keys].
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class NativeKeyDataSourceImpl : NativeKeyDataSource {
    /**
     * Retrieves the secret key from native code.
     *
     * @return The secret key as a [String], or `null` if it cannot be retrieved.
     */
    override fun getSecretKey(): String? {
        return try {
            Keys.getSecretKey()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}