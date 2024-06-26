package com.syauqi.watcheez.core.di

import com.google.gson.GsonBuilder
import com.syauqi.watcheez.core.data.source.network.api.movie.ApiHelperMovie
import com.syauqi.watcheez.core.data.source.network.api.movie.ApiHelperMovieImpl
import com.syauqi.watcheez.core.data.source.network.api.ApiService
import com.syauqi.watcheez.core.data.source.network.api.people.ApiHelperPeople
import com.syauqi.watcheez.core.data.source.network.api.people.ApiHelperPeopleImpl
import com.syauqi.watcheez.utils.AppExecutors
import com.syauqi.watcheez.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val headerInterceptor = Interceptor{
            val originalRequest = it.request()
            val request = originalRequest.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authentication", Constants.ACCESS_TOKEN_AUTH)
                .build()
            it.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieApiHelper(apiHelper: ApiHelperMovieImpl): ApiHelperMovie = apiHelper
    @Singleton
    @Provides
    fun providePeopleApiHelper(apiHelper: ApiHelperPeopleImpl): ApiHelperPeople = apiHelper

    @Singleton
    @Provides
    fun provideAppExecutors() = AppExecutors()
}