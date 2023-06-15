/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.di;

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationLocalDataSource
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationRemoteDataSource
import io.parrotsoftware.qatest.data.config.local.room.daos.CredentialsDao
import io.parrotsoftware.qatest.data.config.local.room.daos.ProductDao
import io.parrotsoftware.qatest.data.config.remote.retrofit.ParrotApiService
import io.parrotsoftware.qatest.data.implementations.authentication.AuthenticationLocalDataSourceImpl
import io.parrotsoftware.qatest.data.implementations.authentication.AuthenticationRemoteDataSourceImpl
import io.parrotsoftware.qatest.data.implementations.product.ProductLocalDataSourceImpl
import io.parrotsoftware.qatest.data.implementations.product.ProductRemoteDataSourceImpl
import io.parrotsoftware.qatest.data.implementations.product.mapper.ProductResponseListToProductListMapper
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.product.datasources.ProductLocalDataSource
import io.parrotsoftware.qatest.data.product.datasources.ProductRemoteDataSource

/**
 * DataSourceModule
 *
 * @author (c) 2023, Parrot Inc.
 */
@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun provideAuthenticationLocalDataSource(
        credentialsDao: CredentialsDao
    ): AuthenticationLocalDataSource = AuthenticationLocalDataSourceImpl(credentialsDao)

    @Provides
    fun provideAuthenticationRemoteDataSource(
        userManagerImpl: UserManagerImpl,
        networkInteractorImpl: NetworkInteractorImpl
    ): AuthenticationRemoteDataSource = AuthenticationRemoteDataSourceImpl(userManagerImpl, networkInteractorImpl)

    @Provides
    fun provideProductLocalDataSource(
        productDao: ProductDao
    ): ProductLocalDataSource = ProductLocalDataSourceImpl(
        productDao
    )

    @Provides
    fun provideProductRemoteDataSource(
        userManagerImpl: UserManagerImpl,
        networkInteractorImpl: NetworkInteractorImpl,
        productResponseListToProductListMapper: ProductResponseListToProductListMapper
    ): ProductRemoteDataSource = ProductRemoteDataSourceImpl(
        userManagerImpl, networkInteractorImpl, productResponseListToProductListMapper
    )
}
