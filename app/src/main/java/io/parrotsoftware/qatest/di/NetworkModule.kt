package io.parrotsoftware.qatest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qatest.data.database.dao.ProductsDao
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.data.repositories.impl.ProductRepositoryImpl
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesUserRepository(
        userManager: UserManager,
        networkInteractor: NetworkInteractor,
    ): UserRepository = UserRepositoryImpl(userManager, networkInteractor)

    @Provides
    fun providesProductRepository(
        networkInteractor: NetworkInteractor,
        productsDao: ProductsDao
    ): ProductRepository = ProductRepositoryImpl(networkInteractor, productsDao)
}
