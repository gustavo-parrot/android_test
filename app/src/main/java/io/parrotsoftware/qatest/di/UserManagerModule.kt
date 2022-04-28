package io.parrotsoftware.qatest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl

@Module
@InstallIn(SingletonComponent::class)
class UserManagerModule {

    @Provides
    fun providesUserManager(
        @ApplicationContext ctx: Context
    ): UserManager = UserManagerImpl(ctx)
}
