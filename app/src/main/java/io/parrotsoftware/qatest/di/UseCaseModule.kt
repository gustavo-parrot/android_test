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
import io.parrotsoftware.qatest.data.authentication.AuthenticationRepository
import io.parrotsoftware.qatest.data.product.ProductRepository
import io.parrotsoftware.qatest.usecase.authentication.LoginUseCase
import io.parrotsoftware.qatest.usecase.authentication.UserExistsUseCase
import io.parrotsoftware.qatest.usecase.product.GetProductsUseCase

/**
 * UseCaseModule
 *
 * @author (c) 2023, Parrot Inc.
 */
@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ) = LoginUseCase(
        authenticationRepository
    )

    @Provides
    fun provideUserExistsUseCase(
        authenticationRepository: AuthenticationRepository
    ) = UserExistsUseCase(
        authenticationRepository
    )

    @Provides
    fun provideGetProductsUseCase(
        productRepository: ProductRepository
    ) = GetProductsUseCase(
        productRepository
    )
}
