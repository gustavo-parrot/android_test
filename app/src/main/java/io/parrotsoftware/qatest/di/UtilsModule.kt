package io.parrotsoftware.qatest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.parrotsoftware.qatest.Util.EntityUtils

@Module
@InstallIn(ViewModelComponent::class)
class UtilsModule {
    @Provides
    fun providesEntityUtils() = EntityUtils()
}
