package com.lushan.securedapp.ui.state

/**
 * A sealed class representing the state of data operations.
 *
 * @param T The type of data being handled.
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Idle : DataState<Nothing>()
}