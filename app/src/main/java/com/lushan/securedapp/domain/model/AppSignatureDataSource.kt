package com.lushan.securedapp.domain.model

/**
 * Interface to retrieve the application's signature.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
interface AppSignatureDataSource {
    fun getAppSignature(): String?
}
