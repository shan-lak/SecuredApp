package com.lushan.securedapp.data.source

import android.content.Context
import com.lushan.securedapp.domain.model.AppSignatureDataSource

/**
 * Implementation of [AppSignatureDataSource] that retrieves the app signature using [AppSignatureHelper].
 *
 * @property context The application context used to access resources.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class AppSignatureDataSourceImpl(private val context: Context) : AppSignatureDataSource {
    /**
     * Retrieves the app signature for the application.
     *
     * @return The app signature as a [String], or `null` if it cannot be retrieved.
     */
    override fun getAppSignature(): String? {
        return AppSignatureHelper.getAppSignatures(context)
    }
}