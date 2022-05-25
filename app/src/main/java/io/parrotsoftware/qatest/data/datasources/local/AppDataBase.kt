package io.parrotsoftware.qatest.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.parrotsoftware.qatest.BuildConfig
import io.parrotsoftware.qatest.data.datasources.local.product.ProductDao
import io.parrotsoftware.qatest.data.model.LocalProduct

@Database(
    version = BuildConfig.DB_VERSION,
    exportSchema = false,
    entities = [LocalProduct::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract val productDao: ProductDao

    companion object {
        private const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.AppDataBase"

        private fun createInstance(ctx: Context): AppDataBase =
            Room.databaseBuilder(ctx, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(ctx: Context) = createInstance(ctx)
    }
}