package com.lushan.securedapp.core.security

/**
 * Object to manage native key retrieval for secure operations.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
object Keys {
    init {
        System.loadLibrary("native-lib")
    }

    external fun getSecretKey(): String
}
