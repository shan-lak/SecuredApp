package com.lushan.securedapp.core.network

import android.util.Log
import com.google.gson.Gson
import com.lushan.securedapp.data.remote.dto.ErrorResponseDto
import com.lushan.securedapp.ui.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

/**
 * A utility function to safely make API calls and handle responses.
 *
 * @param apiCall A suspend function that makes the API call and returns a Response<DtoType>.
 * @param mapper A function that maps the DTO to the desired Domain Model.
 * @return A Flow emitting DataState<DomainType>.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
inline fun <DtoType, DomainType> safeApiCall(
    crossinline apiCall: suspend () -> Response<DtoType>,
    crossinline mapper: (DtoType) -> DomainType,
): Flow<DataState<DomainType>> =
    flow {
        // Immediately emit the Loading state to notify the UI
        emit(DataState.Loading)

        // Execute API Call
        try {
            val response = apiCall()
            Log.d("safeApiCall", "response: $response")
            if (response.isSuccessful) {
                val dto = response.body()!!
                // Perform the mapping from DTO to Domain Model here
                val domainModel = mapper(dto)
                emit(DataState.Success(domainModel))
            } else {
                val errorBody = response.errorBody()?.string()
                val message =
                    try {
                        val errorDto = Gson().fromJson(errorBody, ErrorResponseDto::class.java)
                        errorDto.error
                    } catch (e: IOException) {
                        // Catch specific network-related exceptions
                        e.printStackTrace()
                        "Could not connect to the network. Please check your connection."
                    } catch (e: Exception) {
                        // Catch any other unexpected exceptions.
                        e.printStackTrace()
                        e.message ?: "An unknown error occurred."
                    }
                emit(DataState.Error(message = message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(message = e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
