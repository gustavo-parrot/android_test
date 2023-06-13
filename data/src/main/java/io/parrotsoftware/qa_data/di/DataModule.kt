package io.parrotsoftware.qa_data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qa_data.bd.AppDatabase
import io.parrotsoftware.qa_data.managers.UserManager
import io.parrotsoftware.qa_data.managers.impl.UserManagerImpl
import io.parrotsoftware.qa_data.datasources.ProductDataSource
import io.parrotsoftware.qa_data.datasources.UserLocalDataSource
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.datasources.impl.ProductDataSourceImpl
import io.parrotsoftware.qa_data.datasources.impl.UserLocalDataSourceImpl
import io.parrotsoftware.qa_data.datasources.impl.UserRemoteDataSourceImpl
import io.parrotsoftware.qa_data.managers.ProductManager
import io.parrotsoftware.qa_data.managers.impl.ProductManagerImpl
import io.parrotsoftware.qa_data.mappers.ProductMapper
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
    fun userRemoteDataSourceProvider(
        userManager: UserManager,
        networkInteractor: NetworkInteractor
    ) : UserRemoteDataSource = UserRemoteDataSourceImpl(userManager,networkInteractor)

    @Provides
    @Singleton
    fun userLocalDataSourceProvider(
        userManager: UserManager,
    ) : UserLocalDataSource = UserLocalDataSourceImpl(userManager)


    @Provides
    @Singleton
    fun userRepositoryProvider(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
        ):UserRepository = UserRepository(userRemoteDataSource,userLocalDataSource)

    @Provides
    @Singleton
    fun productDataSourceProvider(
        networkInteractor: NetworkInteractor,
        productManager: ProductManager
    ) : ProductDataSource  = ProductDataSourceImpl(networkInteractor,productManager)

    @Provides
    @Singleton
    fun productManagerProvider(
        appDatabase: AppDatabase,
        productMapper: ProductMapper
    ): ProductManager = ProductManagerImpl(appDatabase,productMapper)

    @Provides
    @Singleton
    fun productRepositoryProvider(
        productDataSource: ProductDataSource
    ):ProductRepository
        =  ProductRepository(productDataSource)

    @Provides
    @Singleton
    fun databaseProvider( @ApplicationContext context: Context) : AppDatabase
    = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database"
    ).build()


}