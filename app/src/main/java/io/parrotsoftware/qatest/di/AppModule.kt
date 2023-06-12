package io.parrotsoftware.qatest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.managers.UserManager
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import javax.inject.Singleton

/***
 * Android hilt module to have dependencies of classes are not android framework
 */

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun userManagerProvider(
        @ApplicationContext context: Context
    ) : UserManager = UserManagerImpl(context)

    /*@Provides
    @Singleton
    fun userRepositoryProvider(
        userManager: UserManager,
        networkInteractor: NetworkInteractor
    ) : UserRepository = UserRepositoryImpl(userManager,networkInteractor)

    @Provides
    @Singleton
    fun productRepositoryProvider(
        networkInteractor: NetworkInteractor
    ) : ProductRepository = ProductRepositoryImpl(networkInteractor)*/

}