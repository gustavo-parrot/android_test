package io.parrotsoftware.qa_network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.BuildConfig
import io.parrotsoftware.qa_network.NetworkBuilder
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qa_network.services.ParrotApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun retrofitProvider() = NetworkBuilder.build(BuildConfig.BASE_URL)

    @Provides
    @Singleton
    fun parrotApiProvider(retrofit: Retrofit) = retrofit.create(ParrotApiService::class.java)


    @Provides
    @Singleton
    fun networkInteractor()  : NetworkInteractor = NetworkInteractorImpl()

}