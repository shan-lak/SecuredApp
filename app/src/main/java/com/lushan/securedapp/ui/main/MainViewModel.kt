package com.lushan.securedapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.domain.usecase.CheckEnvironmentSecurityUseCase
import com.lushan.securedapp.domain.usecase.GetSensitivityDataUseCase
import com.lushan.securedapp.ui.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the main screen of the application.
 *
 * This ViewModel is responsible for managing UI-related data in a lifecycle-conscious way.
 * It interacts with use cases to fetch sensitive data and check the security of the environment.
 *
 * @param getSensitivityDataUseCase Use case for fetching sensitive data.
 * @param environmentSecurityUseCase Use case for checking environment security.
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val getSensitivityDataUseCase: GetSensitivityDataUseCase,
        private val environmentSecurityUseCase: CheckEnvironmentSecurityUseCase,
    ) : ViewModel() {
        /**
         * A [StateFlow] that holds the result of the environment security check.
         * The UI should observe this to react to the security status.
         * It is nullable (`Boolean?`) to represent an initial, undetermined state.
         * - `true`: The environment is secure.
         * - `false`: A security risk (e.g., root access) was detected.
         * - `null`: The check has not completed yet.
         */
        private val _isEnvironmentSecure = MutableStateFlow<Boolean?>(null)
        val isEnvironmentSecure: StateFlow<Boolean?> = _isEnvironmentSecure

        /**
         * A [StateFlow] that represents the current state of the sensitive data fetching operation.
         * The UI should observe this flow to display loading indicators, data, or error messages.
         * See [DataState] for possible states (Idle, Loading, Success, Error).
         */
        private val _dataState = MutableStateFlow<DataState<SensitiveData>>(DataState.Idle)
        val dataState: StateFlow<DataState<SensitiveData>> = _dataState

        init {
            // Perform the security check as soon as the ViewModel is created.
            runSecurityCheck()
        }

        /**
         * Executes the environment security check and updates the [_isEnvironmentSecure] state.
         */
        fun runSecurityCheck() {
            _isEnvironmentSecure.value = environmentSecurityUseCase()
        }

        /**
         * Initiates the asynchronous fetching of sensitive data.
         *
         * This function launches a coroutine in the [viewModelScope], calls the appropriate use case,
         * and collects the resulting flow of [DataState]. Each emitted state is then posted
         * to the [_dataState] StateFlow to update the UI.
         */
        fun fetchSensitiveData() {
            viewModelScope.launch {
                getSensitivityDataUseCase().collect { state ->
                    _dataState.value = state
                }
            }
        }
    }
