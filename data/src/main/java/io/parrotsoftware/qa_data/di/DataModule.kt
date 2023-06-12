package io.parrotsoftware.qa_data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_data.UserManagerD
import io.parrotsoftware.qa_data.UserManagerDImpl
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.datasources.impl.UserRemoteDataSourceImpl
import io.parrotsoftware.qa_data.repositories.UserRepositoryD
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import org.intellij.lang.annotations.PrintFormat
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
        userRemoteDataSource: UserRemoteDataSource
    ):UserRepositoryD = UserRepositoryD(userRemoteDataSource)
}