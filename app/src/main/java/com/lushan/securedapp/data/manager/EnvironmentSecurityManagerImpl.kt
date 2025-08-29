package com.lushan.securedapp.data.manager

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.lushan.securedapp.domain.manager.EnvironmentSecurityManager
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Implementation of [EnvironmentSecurityManager] to check the security status of the device environment.
 * This includes checks for developer mode, emulator detection, and root access.
 *
 * @property context The application context used to access system settings and resources.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class EnvironmentSecurityManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : EnvironmentSecurityManager {
        @Suppress("ktlint:standard:property-naming")
        private val TAG = "AppSecurityCheck"

        /**
         * Checks if the device environment is secure by verifying that developer mode is disabled,
         * the device is not an emulator, and the device is not rooted.
         *
         * @return `true` if the environment is secure, `false` otherwise.
         */
        override fun isEnvironmentSecure(): Boolean {
            if (isDeveloperModeEnabled()) {
                Log.w(TAG, "Developer mode is enabled")
                return false
            }
            if (isEmulator()) {
                Log.w(TAG, "Emulator detected")
                return false
            }
            if (isRooted()) {
                Log.w(TAG, "Device is rooted")
                return false
            }
            Log.i(TAG, "Environment is secure")
            return true
        }

        /**
         * Checks if developer mode is enabled on the device.
         *
         * @return `true` if developer mode is enabled, `false` otherwise.
         */
        private fun isDeveloperModeEnabled(): Boolean =
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                0,
            ) != 0

        /**
         * Checks if the device is an emulator.
         *
         * @return `true` if the device is an emulator, `false` otherwise.
         */
        fun isEmulator(): Boolean = checkBuildProperties() || checkQemuProperty()

        /**
         * Checks various build properties to identify if the device is an emulator.
         *
         * @return `true` if the build properties indicate an emulator, `false` otherwise.
         */
        private fun checkBuildProperties(): Boolean {
            val fingerprint = Build.FINGERPRINT ?: ""
            val model = Build.MODEL ?: ""
            val manufacturer = Build.MANUFACTURER ?: ""
            val brand = Build.BRAND ?: ""
            val device = Build.DEVICE ?: ""
            val product = Build.PRODUCT ?: ""

            return fingerprint.startsWith("generic") ||
                fingerprint.startsWith("unknown") ||
                model.contains("google_sdk") ||
                model.contains("Emulator") ||
                model.contains("Android SDK built for x86") ||
                model.contains("Android SDK built for x86_64") ||
                manufacturer.contains("Genymotion") ||
                (brand.startsWith("generic") && device.startsWith("generic")) ||
                product == "google_sdk"
        }

        /**
         * Checks the system property "ro.kernel.qemu" to determine if the device is an emulator.
         *
         * @return `true` if the property indicates an emulator, `false` otherwise.
         */
        private fun checkQemuProperty(): Boolean =
            try {
                val cls = Class.forName("android.os.SystemProperties")
                val method = cls.getMethod("get", String::class.java)
                val value = method.invoke(null, "ro.kernel.qemu") as String
                value == "1"
            } catch (e: Exception) {
                false
            }

        /**
         * Checks if the device is rooted using the RootBeer library.
         *
         * @return `true` if the device is rooted, `false` otherwise.
         */
        private fun isRooted(): Boolean = RootBeer(context).isRooted
    }
