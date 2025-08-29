package com.lushan.securedapp.data.remote

import com.lushan.securedapp.core.constant.NetworkConstants.HEADER_APP_SIGNATURE
import com.lushan.securedapp.core.constant.NetworkConstants.HEADER_NONCE
import com.lushan.securedapp.core.constant.NetworkConstants.HEADER_PAYLOAD_SIGNATURE
import com.lushan.securedapp.domain.model.AppSignatureDataSource
import com.lushan.securedapp.domain.model.NativeKeyDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * An OkHttp interceptor that adds security headers to each outgoing request.
 *
 * This interceptor retrieves the app signature and native key from their respective data sources,
 * generates a payload signature using HMAC, and adds these values as headers to the request.
 * If the required security credentials are not found, a [SecurityException] is thrown.
 *
 * @property appSignatureDataSource The data source for retrieving the app signature.
 * @property nativeKeyDataSource The data source for retrieving the native key.
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */

@Singleton
class SecurityInterceptor
    @Inject
    constructor(
        private val appSignatureDataSource: AppSignatureDataSource,
        private val nativeKeyDataSource: NativeKeyDataSource,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            // Get the app signature
            val appSignature = appSignatureDataSource.getAppSignature()
            // Get the native key
            val nativeKey = nativeKeyDataSource.getSecretKey()

            if (appSignature == null && nativeKey == null) {
                throw SecurityException("Security credentials not found")
            }

            // Create security header
            val nonce = System.currentTimeMillis().toString()
            val signingKey = "$appSignature.$nonce"
            val payloadSignature = HmacGenerator.generateHmac(signingKey, nativeKey!!)

            // Add the app signature and native key to the request headers
            val newRequest =
                originalRequest
                    .newBuilder()
                    .addHeader(HEADER_APP_SIGNATURE, appSignature ?: "")
                    .addHeader(HEADER_NONCE, nonce)
                    .addHeader(HEADER_PAYLOAD_SIGNATURE, payloadSignature)
                    .build()

            return chain.proceed(newRequest)
        }
    }
