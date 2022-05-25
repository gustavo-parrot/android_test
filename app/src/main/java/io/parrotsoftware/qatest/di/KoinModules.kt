package io.parrotsoftware.qatest.di

import android.content.Context
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qatest.data.datasources.local.AppDataBase
import io.parrotsoftware.qatest.data.datasources.local.product.ProductDao
import io.parrotsoftware.qatest.data.datasources.local.user.UserManager
import io.parrotsoftware.qatest.data.datasources.local.user.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import io.parrotsoftware.qatest.data.repositories.UserRepository
import io.parrotsoftware.qatest.data.repositories.impl.ProductRepositoryImpl
import io.parrotsoftware.qatest.data.repositories.impl.UserRepositoryImpl
import io.parrotsoftware.qatest.ui.list.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ListViewModel(get(), get()) }
}

val repositoriesModule = module {
    fun provideUserRepository(
        userManager: UserManager,
        networkInteractor: NetworkInteractor
    ): UserRepository {
        return UserRepositoryImpl(userManager, networkInteractor)
    }

    fun provideProductRepository(
        networkInteractor: NetworkInteractor,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(networkInteractor, productDao)
    }

    factory { provideUserRepository(get(), get()) }
    factory { provideProductRepository(get(), get()) }
}

val remoteDataSourceModule: Module = module {
    fun provideNetworkInteractor(): NetworkInteractor {
        return NetworkInteractorImpl()
    }

    single { provideNetworkInteractor() }
}

val localDataSourceModule: Module = module {
    fun provideUserManager(context: Context): UserManager {
        return UserManagerImpl(context)
    }

    fun provideAppDatabase(context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }
    fun provideProductDao(db: AppDataBase): ProductDao {
        return db.productDao
    }

    single { provideUserManager(androidApplication()) }
    single { provideAppDatabase(androidApplication()) }
    single { provideProductDao(get()) }
}