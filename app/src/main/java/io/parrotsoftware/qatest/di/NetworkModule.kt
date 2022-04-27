package io.parrotsoftware.qatest.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.data.repositories.impl.ProductRepositoryImpl
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @Singleton
    fun providesProductRepository():ProductRepository = ProductRepositoryImpl()
}
