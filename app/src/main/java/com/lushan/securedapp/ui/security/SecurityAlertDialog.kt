package com.lushan.securedapp.ui.security

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.lushan.securedapp.ui.theme.SecuredAppTheme

@Suppress("ktlint:standard:function-naming")
/**
 * Composable function to display a security alert dialog.
 *
 * @param showDialog Boolean flag to control the visibility of the dialog.
 * @param onConfirm Lambda function to be executed when the user confirms the dialog.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Composable
fun SecurityAlertDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = "Security Alert")
            },
            text = {
                Text(
                    text = @Suppress("ktlint:standard:max-line-length")
                    "For your safety, this app can only be used on a secure device. Please disable developer options or use a non-rooted device to continue.",
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Exit App")
                }
            },
            dismissButton = null,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview(showBackground = true)
@Composable
fun SecurityAlertDialogPreview() {
    SecuredAppTheme {
        SecurityAlertDialog(
            showDialog = true,
            onConfirm = { },
        )
    }
}
