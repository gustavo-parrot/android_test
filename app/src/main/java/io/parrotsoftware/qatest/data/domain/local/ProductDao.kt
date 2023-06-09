package io.parrotsoftware.qatest.data.domain.local

import androidx.room.Dao

import androidx.room.ColumnInfo
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity("product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    @Embedded(prefix = "category_bean") val category: Category
)

@Entity
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val id: String,
    val name: String,
    val position: Int
)

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listProduct: List<Product>)

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Delete
    suspend fun delete(product: Product)

}