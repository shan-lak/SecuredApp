package com.lushan.securedapp.domain.repository

import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.ui.state.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling data operations.
 * This repository is responsible for fetching data from a remote backend.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
interface SecurityRepository {
    suspend fun getSensitiveData(): Flow<DataState<SensitiveData>>
}
