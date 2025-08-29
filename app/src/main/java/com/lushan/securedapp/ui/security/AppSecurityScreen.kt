package com.lushan.securedapp.ui.security

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lushan.securedapp.ui.main.MainViewModel

@Suppress("ktlint:standard:function-naming")
/**
 * Composable function to handle app security checks and display appropriate UI based on the security status.
 *
 * @param viewModel The MainViewModel instance to access security state.
 * @param onExitApp Callback function to be invoked when the user needs to exit the app due to security issues.
 * @param onSuccess Composable content to be displayed when the environment is secure.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Composable
fun AppSecurityScreen(
    viewModel: MainViewModel,
    onExitApp: () -> Unit,
    onSuccess: @Composable () -> Unit,
) {
    val isEnvironmentSecure by viewModel.isEnvironmentSecure.collectAsStateWithLifecycle()
    AppSecurityScreenContent(isEnvironmentSecure, onExitApp, onSuccess)
}

@Suppress("ktlint:standard:function-naming")
/**
 * Composable function to display content based on the security status of the environment.
 *
 * @param isSecure A nullable Boolean indicating if the environment is secure (`true`), not secure (`false`), or still being checked (`null`).
 * @param onExitApp Callback function to be invoked when the user needs to exit the app due to security issues.
 * @param onSuccess Composable content to be displayed when the environment is secure.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Composable
fun AppSecurityScreenContent(
    isSecure: Boolean?,
    onExitApp: () -> Unit,
    onSuccess: @Composable () -> Unit,
) {
    when (isSecure) {
        true -> {
            onSuccess()
        }
        false -> {
            // The environment is NOT secure, show the blocking dialog
            SecurityAlertDialog(
                showDialog = true,
                onConfirm = onExitApp,
            )
        }
        null -> {
            // Show a loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}
