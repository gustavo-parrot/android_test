package io.parrotsoftware.qa_data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_data.UserManagerD
import io.parrotsoftware.qa_data.UserManagerDImpl
import io.parrotsoftware.qa_data.datasources.ProductRemoteDataSource
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.datasources.impl.ProductRemoteDataSourceImpl
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
    ) : UserManagerD = UserManagerDImpl(context)

    @Provides
    @Singleton
    fun userRemoteDataSource(
        userManagerD: UserManagerD,
        networkInteractor: NetworkInteractor
    ) : UserRemoteDataSource = UserRemoteDataSourceImpl(userManagerD,networkInteractor)

    @Provides
    @Singleton
    fun userRepositoryDProvider(
        userRemoteDataSource: UserRemoteDataSource,
        userManagerD: UserManagerD
        ):UserRepository = UserRepository(userRemoteDataSource,userManagerD)

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