package io.parrotsoftware.qa_network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl

@Module
@InstallIn(SingletonComponent::class)
object NetworkPackModule {
    @Provides
    fun provNetworkInteractor(): NetworkInteractor = NetworkInteractorImpl()
}
