package com.lushan.securedapp.domain.model

/**
 * Data class representing sensitive data that needs to be protected.
 *
 * @property message A general message related to the sensitive data.
 * @property data An instance of [SecureData] containing the actual sensitive information.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
data class SensitiveData(
    val message: String,
    val data: SecureData
)

data class SecureData(
    val title: String,
    val description: String
)
