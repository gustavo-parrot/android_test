/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.di;

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.authentication.AuthenticationRepository
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationLocalDataSource
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationRemoteDataSource
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.product.ProductRepository
import io.parrotsoftware.qatest.data.product.datasources.ProductLocalDataSource
import io.parrotsoftware.qatest.data.product.datasources.ProductRemoteDataSource
import io.parrotsoftware.qatest.data.store.StoreRepository
import io.parrotsoftware.qatest.data.store.datasources.StoreLocalDataSource
import io.parrotsoftware.qatest.data.store.datasources.StoreRemoteDataSource
import javax.inject.Singleton

/**
 * RepositoryModule
 *
 * @author (c) 2023, Parrot Inc.
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authenticationLocalDataSource: AuthenticationLocalDataSource,
        authenticationRemoteDataSource: AuthenticationRemoteDataSource
    ): AuthenticationRepository =
        AuthenticationRepository(authenticationLocalDataSource, authenticationRemoteDataSource)

    @Provides
    @Singleton
    fun provideProductRepository(
        productLocalDataSource: ProductLocalDataSource,
        productRemoteDataSource: ProductRemoteDataSource
    ): ProductRepository = ProductRepository(productLocalDataSource, productRemoteDataSource)

    @Provides
    @Singleton
    fun provideStoreRepository(
        storeLocalDataSource: StoreLocalDataSource,
        storeRemoteDataSource: StoreRemoteDataSource
    ): StoreRepository = StoreRepository(storeLocalDataSource, storeRemoteDataSource)

    @Provides
    @Singleton
    fun provideUserManagerImpl(
        context: Context
    ): UserManagerImpl {
        return UserManagerImpl(context)
    }
}
