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
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.config.local.room.ParrotDataBase
import io.parrotsoftware.qatest.data.config.local.room.daos.CategoryDao
import io.parrotsoftware.qatest.data.config.local.room.daos.CredentialsDao
import io.parrotsoftware.qatest.data.config.local.room.daos.ProductDao
import io.parrotsoftware.qatest.data.config.local.room.daos.StoreDao
import javax.inject.Singleton

/**
 * DataBaseModule
 *
 * @author (c) 2023, Parrot Inc.
 */
@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    companion object {
        const val APPLICATION_DB_NAME = "parrot_challenge_db"
    }

    @Singleton
    @Provides
    fun provideParrotDataBase(application: Application): ParrotDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            ParrotDataBase::class.java,
            APPLICATION_DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(parrotDataBase: ParrotDataBase): CategoryDao =
        parrotDataBase.categoryDao()

    @Singleton
    @Provides
    fun provideCredentialsDao(parrotDataBase: ParrotDataBase): CredentialsDao =
        parrotDataBase.credentialsDao()

    @Singleton
    @Provides
    fun provideProductDao(parrotDataBase: ParrotDataBase): ProductDao =
        parrotDataBase.productDao()

    @Singleton
    @Provides
    fun provideStoreDao(parrotDataBase: ParrotDataBase): StoreDao =
        parrotDataBase.storeDao()
}
