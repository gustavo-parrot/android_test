package io.parrotsoftware.qatest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainContextModule {
    @Provides
    @Singleton
    fun providesContext(@ApplicationContext appContext: Context): Context = appContext
}
