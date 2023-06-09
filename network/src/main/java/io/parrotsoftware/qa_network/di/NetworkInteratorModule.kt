package io.parrotsoftware.qa_network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkInteractorModule {

    @Singleton
    @Provides
    fun safeCall(): NetworkInteractor = NetworkInteractorImpl()

}
