package io.parrotsoftware.qatest.data.domain

import io.parrotsoftware.qatest.data.model.LocalProduct

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    val isAvailable: Boolean,
    val category: Category,
)

fun List<Product>.convertToLocalObjects(): List<LocalProduct> {
    val listToReturn = mutableListOf<LocalProduct>()
    this.forEach { product ->
        listToReturn.add(
            LocalProduct(
                id = product.id ,
                name = product.name,
                description = product.description,
                image = product.image,
                price = product.price,
                isAvailable = product.isAvailable,
                categoryId = product.category.id,
                categoryName = product.category.name,
                categoryPosition = product.category.position
            )
        )
    }
    return listToReturn
}