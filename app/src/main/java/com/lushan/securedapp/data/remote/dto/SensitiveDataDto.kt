package com.lushan.securedapp.data.remote.dto

/**
 * Data Transfer Object representing sensitive data received from the API.
 *
 * @property message A string containing a message from the API.
 * @property data An instance of [SecureDataDto] containing secure information.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
data class SensitiveDataDto(
    val message: String,
    val data: SecureDataDto,
)

data class SecureDataDto(
    val title: String,
    val description: String,
)
