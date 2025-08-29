package com.lushan.securedapp.core.constant

/**
 * Object holding network-related constants such as header names and base URL.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
object NetworkConstants {
    const val HEADER_APP_SIGNATURE = "X-App-Signature"
    const val HEADER_NONCE = "X-Nonce"
    const val HEADER_PAYLOAD_SIGNATURE = "X-Payload-Signature"

    const val BASE_URL = "https://securedappbackend.onrender.com/"
}
