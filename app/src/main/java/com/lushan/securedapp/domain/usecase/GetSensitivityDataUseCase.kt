package com.lushan.securedapp.domain.usecase

import com.lushan.securedapp.domain.model.SensitiveData
import com.lushan.securedapp.domain.repository.SecurityRepository
import com.lushan.securedapp.ui.state.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving sensitive data from the repository.
 * It returns a [Flow] that emits [DataState] objects, representing
 * the current state of the data fetching operation (loading, success, error).
 *
 * @param repository The repository used to fetch sensitive data.
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
class GetSensitivityDataUseCase
    @Inject
    constructor(
        private val repository: SecurityRepository,
    ) {
        suspend operator fun invoke(): Flow<DataState<SensitiveData>> = repository.getSensitiveData()
    }
