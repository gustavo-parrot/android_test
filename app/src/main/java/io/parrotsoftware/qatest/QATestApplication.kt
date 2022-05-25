package io.parrotsoftware.qatest

import android.app.Application
import io.parrotsoftware.qatest.di.localDataSourceModule
import io.parrotsoftware.qatest.di.remoteDataSourceModule
import io.parrotsoftware.qatest.di.repositoriesModule
import io.parrotsoftware.qatest.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class QATestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@QATestApplication)
            modules(
                viewModelsModule,
                repositoriesModule,
                remoteDataSourceModule,
                localDataSourceModule
            )
        }
    }
}