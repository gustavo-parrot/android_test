package io.parrotsoftware.qa_data.bd.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.parrotsoftware.qa_data.bd.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts() : List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products : List<ProductEntity>)

    @Query("UPDATE ProductEntity SET isAvailable = :available WHERE id = :productId")
    fun updateProduct(productId:String, available:Boolean)

}