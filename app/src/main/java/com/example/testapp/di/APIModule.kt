package com.example.testapp.di

import com.example.testapp.config.AppConfig
import com.example.testapp.data.api.IUserAPI
import com.example.testapp.data.dataStore.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {


    @Provides
    fun provideBaseUrl() = AppConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideAuthInterceptor(preference: UserPreference): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            val token = runBlocking(Dispatchers.IO) {
                preference.getUserToken()
            }

            request.addHeader("Authorization", "Bearer ${token ?: ""}")
            val actualRequest = request.build()
            val response = it.proceed(actualRequest)
            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                if (!response.toString().contains("login") || !response.toString()
                        .contains("signup")
                ) {
                    runBlocking(Dispatchers.IO) {
                        preference.clearUser()
                    }
                }
            }
            return@Interceptor response
        }
    }

    @Provides
    @Singleton
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit): IUserAPI = retrofit.create(IUserAPI::class.java)

}