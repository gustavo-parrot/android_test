package io.parrotsoftware.qa_data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import io.parrotsoftware.qa_data.bd.daos.ProductDao
import io.parrotsoftware.qa_data.bd.entities.CategoryEntity
import io.parrotsoftware.qa_data.bd.entities.ProductEntity

@Database(entities = [ProductEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}