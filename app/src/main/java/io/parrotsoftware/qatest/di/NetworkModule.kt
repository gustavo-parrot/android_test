/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.di;

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.BuildConfig
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qatest.data.config.remote.retrofit.ParrotApiService
import io.parrotsoftware.qatest.data.config.remote.retrofit.converterfactory.NullOnEmptyConverterFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * NetworkModule
 *
 * @author (c) 2023, Parrot Inc.
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        private const val TIME_OUT = 20L
    }

    @Provides
    @Named("debuggable")
    fun provideDebuggableFlag() = true

    @Provides
    @Singleton
    fun provideNullOnEmptyConverterFactory(): NullOnEmptyConverterFactory =
        NullOnEmptyConverterFactory.create()

    @Provides
    fun provideOkHttpClient(
        @Named("debuggable")
        debuggable: Boolean,
        connectionSpec: ConnectionSpec
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okhttp = OkHttpClient.Builder()
        okhttp.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        okhttp.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        if (debuggable) {
            okhttp.addInterceptor(loggingInterceptor)
        }
        okhttp.connectionSpecs(listOf(connectionSpec))
        return okhttp.build()
    }

    @Provides
    fun getRetrofit(
        okHttpClient: OkHttpClient,
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun provideParrotApiService(
        retrofit: Retrofit
    ): ParrotApiService = retrofit.create(ParrotApiService::class.java)

    @Provides
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideNetworkInteractorImpl() : NetworkInteractorImpl {
        return NetworkInteractorImpl()
    }
}
