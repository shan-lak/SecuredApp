package com.lushan.securedapp.data.repository

import com.lushan.securedapp.core.network.safeApiCall
import com.lushan.securedapp.data.mapper.toDomain
import com.lushan.securedapp.data.remote.APIService
import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.domain.repository.SecurityRepository
import com.lushan.securedapp.ui.state.DataState
import kotlinx.coroutines.flow.Flow

/**
 * The default implementation of the [SecurityRepository] interface.
 * This repository is responsible for handling all data operations,for sensitive data.
 *
 * @param apiService The remote API service dependency used for network requests.
 * @author [Your Name or Team Name]
 * @since 29-08-2025
 */
class SecurityRepositoryImpl(
    private val apiService: APIService,
) : SecurityRepository {
    /**
     * Retrieves sensitive data from the remote data source.
     *
     * This function makes a network call using the [apiService]. The call is wrapped
     * in the [safeApiCall] utility to ensure that all possible states, such as
     * loading, success, and error, are handled gracefully and emitted as a [DataState].
     *
     * Upon a successful API response, the data transfer object (DTO) is mapped
     * to a [SensitiveData] domain model using the `.toDomain()` extension function.
     *
     * @return A [Flow] that emits [DataState] objects, representing the
     * current state of the data fetching operation.
     */
    override suspend fun getSensitiveData(): Flow<DataState<SensitiveData>> =
        safeApiCall(apiCall = { apiService.getSensitiveData() }, mapper = { it.toDomain() })
}
