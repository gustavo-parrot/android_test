package io.parrotsoftware.qatest.data.repositories

import io.parrotsoftware.qatest.data.database.entities.ProductEntity
import io.parrotsoftware.qatest.data.domain.Product
import io.parrotsoftware.qatest.data.domain.RepositoryResult

interface ProductRepository {

    suspend fun getProducts(accessToken: String, storeId: String): RepositoryResult<List<Product>>

    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ): RepositoryResult<Nothing>

    suspend fun getProductsFromDB(): RepositoryResult<List<Product>>

    suspend fun saveProductList(products: List<ProductEntity>)
}
