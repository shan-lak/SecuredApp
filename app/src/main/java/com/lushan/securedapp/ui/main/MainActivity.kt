package com.lushan.securedapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.lushan.securedapp.ui.security.AppSecurityScreen
import com.lushan.securedapp.ui.theme.SecuredAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity of the application that sets up the UI and handles security checks.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecuredAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Security check screen
                    AppSecurityScreen(
                        viewModel = viewModel,
                        onExitApp = { finish() },
                        onSuccess = { MainScreen(viewModel) },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-run the security check when the app comes to the foreground
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            viewModel.runSecurityCheck()
        }
    }
}
