package io.parrotsoftware.qatest.data.datasources.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.parrotsoftware.qatest.data.model.LocalProduct

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<LocalProduct>)

    @Query("SELECT * FROM Products")
    suspend fun getItems(): List<LocalProduct>
}