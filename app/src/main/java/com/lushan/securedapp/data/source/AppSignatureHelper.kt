package com.lushan.securedapp.data.source

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import com.lushan.securedapp.core.constant.SecurityConstants.HASH_ALGORITHM
import java.security.MessageDigest

/**
 * Utility object for retrieving the app's signature.
 *
 * This object provides a method to get the app's signature, which can be used for security purposes
 * such as verifying the authenticity of the app when communicating with a backend server.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
object AppSignatureHelper {

    /**
     * Retrieves the app's signature.
     *
     * @param context The application context used to access package information.
     * @return The app's signature as a Base64-encoded string, or `null` if it cannot be retrieved.
     */
    fun getAppSignatures(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            val signatures = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            ).signingInfo?.apkContentsSigners

            //Get first signature
            val signature = signatures?.firstOrNull() ?: return null
            val signatureBytes = signature.toByteArray()

            val digest =
                MessageDigest.getInstance(HASH_ALGORITHM).digest(signatureBytes)

            return Base64.encodeToString(digest, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}