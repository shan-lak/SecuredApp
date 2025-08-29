package com.lushan.securedapp.domain.model

/**
 * Interface to retrieve the native secret key used for cryptographic operations.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
interface NativeKeyDataSource {
    fun getSecretKey(): String?
}