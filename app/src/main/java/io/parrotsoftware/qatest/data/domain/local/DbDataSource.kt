package io.parrotsoftware.qatest.data.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class, Category::class], version = 1)
abstract class DbDataSource : RoomDatabase() {

    abstract fun productDao(): ProductDao
}