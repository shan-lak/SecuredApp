package com.lushan.securedapp.data.remote

import android.util.Base64
import com.lushan.securedapp.core.constant.SecurityConstants.HMAC_ALGORITHM
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Utility object for generating HMAC signatures.
 *
 * This object provides a method to generate an HMAC signature using a given signing key and secret key.
 * The generated HMAC is encoded in Base64 format.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
object HmacGenerator {
    /**
     * Generates an HMAC signature.
     *
     * @param signInKey The signing key, a combination of app signature and nonce.
     * @param secretKey The secret key used for HMAC generation.
     * @return The generated HMAC signature encoded in Base64 format.
     */
    fun generateHmac(
        signInKey: String,
        secretKey: String,
    ): String {
        val keySpec = SecretKeySpec(secretKey.toByteArray(), HMAC_ALGORITHM)
        val mac = Mac.getInstance(HMAC_ALGORITHM)

        mac.init(keySpec)
        val hmacBytes = mac.doFinal(signInKey.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(hmacBytes, Base64.NO_WRAP)
    }
}
