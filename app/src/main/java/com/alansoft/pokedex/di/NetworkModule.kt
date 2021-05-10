package com.alansoft.pokedex.di

import android.util.Log
import com.alansoft.pokedex.data.api.DemoApi
import com.google.gson.GsonBuilder
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

/**
 * Created by LEE MIN KYU on 2021/05/09
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .build()
                )
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor {
                val originalRequest = it.request()
                val request = originalRequest.newBuilder().url(originalRequest.url).build()
                Log.d("OkHttp", request.toString())
                it.proceed(request)
            })
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://demo0928971.mockable.io/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .create()
                )
            )
            .client(client)
            .build()

    @Singleton
    @Provides
    fun `providePokeApi`(retrofit: Retrofit): DemoApi =
        retrofit.create(DemoApi::class.java)
}