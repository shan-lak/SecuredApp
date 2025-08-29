package com.lushan.securedapp.data.remote

import com.lushan.securedapp.data.remote.dto.SensitiveDataDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit service interface for making API calls to fetch sensitive data.
 * This interface defines the endpoints and request methods for interacting with the remote API.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
interface APIService {
    @GET("api/v1/sensitive-data")
    suspend fun getSensitiveData(): Response<SensitiveDataDto>
}
