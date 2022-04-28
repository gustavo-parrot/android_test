package io.parrotsoftware.qatest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.parrotsoftware.qatest.data.database.ProductsDB
import io.parrotsoftware.qatest.data.database.dao.ProductsDao

@Module
@InstallIn(SingletonComponent::class)
class DBmodule {

    private val DATA_BASE_NAME = "product_database"

    @Provides
    fun providesProductDatabase(context: Context): ProductsDB =
        Room.databaseBuilder(context, ProductsDB::class.java, DATA_BASE_NAME).build()

    @Provides
    fun providesProductDao(productDatabase: ProductsDB): ProductsDao =
        productDatabase.getProductDao()
}
