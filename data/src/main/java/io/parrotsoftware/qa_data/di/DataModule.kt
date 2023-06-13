package io.parrotsoftware.qa_data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_data.managers.UserManager
import io.parrotsoftware.qa_data.managers.impl.UserManagerImpl
import io.parrotsoftware.qa_data.datasources.ProductRemoteDataSource
import io.parrotsoftware.qa_data.datasources.UserLocalDataSource
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.datasources.impl.ProductRemoteDataSourceImpl
import io.parrotsoftware.qa_data.datasources.impl.UserLocalDataSourceImpl
import io.parrotsoftware.qa_data.datasources.impl.UserRemoteDataSourceImpl
import io.parrotsoftware.qa_data.repositories.ProductRepository
import io.parrotsoftware.qa_data.repositories.UserRepository
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun userManagerProvider(
        @ApplicationContext context: Context
    ) : UserManager = UserManagerImpl(context)

    @Provides
    @Singleton
    fun userRemoteDataSource(
        userManager: UserManager,
        networkInteractor: NetworkInteractor
    ) : UserRemoteDataSource = UserRemoteDataSourceImpl(userManager,networkInteractor)

    @Provides
    @Singleton
    fun userLocalDataSource(
        userManager: UserManager,
    ) : UserLocalDataSource = UserLocalDataSourceImpl(userManager)


    @Provides
    @Singleton
    fun userRepositoryDProvider(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
        ):UserRepository = UserRepository(userRemoteDataSource,userLocalDataSource)

    @Provides
    @Singleton
    fun productRemoteDataSource(
        networkInteractor: NetworkInteractor
    ) : ProductRemoteDataSource  = ProductRemoteDataSourceImpl(networkInteractor)

    @Provides
    @Singleton
    fun productRepositoryDProvider(
        productRemoteDataSource: ProductRemoteDataSource
    ):ProductRepository
        =  ProductRepository(productRemoteDataSource)


}