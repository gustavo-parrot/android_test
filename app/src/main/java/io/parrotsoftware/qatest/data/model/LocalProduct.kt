package io.parrotsoftware.qatest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.parrotsoftware.qatest.data.domain.Category
import io.parrotsoftware.qatest.data.domain.Product

@Entity(tableName = "Products")
data class LocalProduct(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    val categoryId: String,
    val categoryName: String,
    val categoryPosition: Int
)

fun List<LocalProduct>.convertToDomainObjects(): List<Product> {
    val listToReturn = mutableListOf<Product>()
    this.forEach { localProduct ->
        listToReturn.add(
            Product(
                id = localProduct.id ,
                name = localProduct.name,
                description = localProduct.description,
                image = localProduct.image,
                price = localProduct.price,
                isAvailable = localProduct.isAvailable,
                category = Category(
                    id = localProduct.categoryId,
                    name = localProduct.categoryName,
                    position = localProduct.categoryPosition
                ),
            )
        )
    }
    return listToReturn
}