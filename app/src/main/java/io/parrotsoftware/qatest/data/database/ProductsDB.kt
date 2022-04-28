package io.parrotsoftware.qatest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.parrotsoftware.qatest.data.database.dao.ProductsDao
import io.parrotsoftware.qatest.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductsDB : RoomDatabase() {
    abstract fun getProductDao(): ProductsDao
}
