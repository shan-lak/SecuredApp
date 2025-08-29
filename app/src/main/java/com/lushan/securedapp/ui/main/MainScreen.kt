package com.lushan.securedapp.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.ui.state.DataState
import com.lushan.securedapp.ui.theme.SecuredAppTheme

@Suppress("ktlint:standard:function-naming")
/**
 * Main screen composable that displays the UI and interacts with the [MainViewModel].
 * It observes the [dataState] from the ViewModel and updates the UI accordingly.
 *
 * @param viewModel The ViewModel that provides the data and handles user interactions.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val dataState by viewModel.dataState.collectAsStateWithLifecycle()
    MainScreenContent(dataState = dataState, onFetchDataClicked = { viewModel.fetchSensitiveData() })
}

@Suppress("ktlint:standard:function-naming")
/**
 * Composable function that defines the main screen content.
 * It displays a title, a button to fetch data, and the current state of the data (loading, success, error).
 *
 * @param dataState The current state of the data being fetched.
 * @param onFetchDataClicked Callback function to be invoked when the fetch data button is clicked.
 * @param modifier Optional [Modifier] for styling and layout adjustments.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Composable
fun MainScreenContent(
    dataState: DataState<SensitiveData>,
    onFetchDataClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Secure API APP", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = "Click the button below to fetching sensitive data...", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = onFetchDataClicked) {
            Text(text = "Fetch Data")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        // Observe the data state
        when (dataState) {
            is DataState.Loading -> {
                Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
            }
            is DataState.Success<SensitiveData> -> {
                val data = dataState.data
                Text(text = "Data: ${data.message}", style = MaterialTheme.typography.bodyMedium)
            }
            is DataState.Error -> {
                val message = dataState.message
                Text(text = "Error: $message", style = MaterialTheme.typography.bodyMedium)
            }
            is DataState.Idle -> {
                Text(text = "", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    SecuredAppTheme {
        MainScreenContent(dataState = DataState.Loading, onFetchDataClicked = {})
    }
}
