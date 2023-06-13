package io.parrotsoftware.qa_data.managers

import io.parrotsoftware.qa_data.domain.Product

interface ProductManager {
    suspend fun saveProducts(products:List<Product>)
    suspend fun getProducts() : List<Product>
    suspend fun updateProduct(productId: String, available: Boolean)
}