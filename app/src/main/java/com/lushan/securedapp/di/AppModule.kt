package com.lushan.securedapp.di

import android.content.Context
import com.lushan.securedapp.data.remote.APIService
import com.lushan.securedapp.core.constant.NetworkConstants.BASE_URL
import com.lushan.securedapp.data.remote.SecurityInterceptor
import com.lushan.securedapp.data.repository.SecurityRepositoryImpl
import com.lushan.securedapp.data.source.AppSignatureDataSourceImpl
import com.lushan.securedapp.data.source.NativeKeyDataSourceImpl
import com.lushan.securedapp.domain.model.AppSignatureDataSource
import com.lushan.securedapp.domain.model.NativeKeyDataSource
import com.lushan.securedapp.domain.repository.SecurityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides application-level dependencies such as Retrofit, OkHttpClient,
 * APIService, and data sources.
 *
 * This module is installed in the [SingletonComponent], making the provided dependencies
 * available throughout the entire application lifecycle.
 *
 * @author Lushan Lakpriya
 * @since 29-08-2025
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** Provides a singleton instance of [OkHttpClient] configured with logging and security interceptors.
     *
     * @param securityInterceptor The interceptor that adds security headers to requests.
     * @return A configured [OkHttpClient] instance.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(securityInterceptor: SecurityInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(securityInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /** Provides a singleton instance of [Retrofit] configured with the base URL and OkHttpClient.
     *
     * @param okHttpClient The OkHttpClient to be used for network requests.
     * @return A configured [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** Provides a singleton instance of [APIService] created from the Retrofit instance.
     *
     * @param retrofit The Retrofit instance used to create the APIService.
     * @return A singleton [APIService] instance.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    /** Provides a singleton instance of [SecurityInterceptor] with required data sources.
     *
     * @param appSignatureDataSource The data source for retrieving the app signature.
     * @param nativeKeyDataSource The data source for retrieving the native key.
     * @return A configured [SecurityInterceptor] instance.
     */
    @Provides
    @Singleton
    fun provideSecurityInterceptor(
        appSignatureDataSource: AppSignatureDataSource,
        nativeKeyDataSource: NativeKeyDataSource): SecurityInterceptor {
        return SecurityInterceptor(appSignatureDataSource, nativeKeyDataSource)
    }

    /** Provides a singleton instance of [AppSignatureDataSource] implementation.
     *
     * @param context The application context used to access resources.
     * @return A singleton [AppSignatureDataSource] instance.
     */
    @Provides
    @Singleton
    fun provideAppSignatureDataSource(@ApplicationContext context: Context): AppSignatureDataSource {
        return AppSignatureDataSourceImpl(context)
    }

    /** Provides a singleton instance of [NativeKeyDataSource] implementation.
     *
     * @return A singleton [NativeKeyDataSource] instance.
     */
    @Provides
    @Singleton
    fun provideNativeKeyDataSource(): NativeKeyDataSource {
        return NativeKeyDataSourceImpl()
    }

    /** Provides a singleton instance of [SecurityRepository] implementation.
     *
     * @param apiService The APIService used for network operations.
     * @return A singleton [SecurityRepository] instance.
     */
    @Provides
    @Singleton
    fun provideSecurityRepository(
        apiService: APIService,
    ): SecurityRepository {
        return SecurityRepositoryImpl(apiService)
    }
}