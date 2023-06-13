package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.datasources.ProductDataSource
import javax.inject.Inject

class ProductRepository @Inject
constructor(private val productDataSource: ProductDataSource) {

suspend fun getProducts(accessToken: String, storeId: String): RepositoryResult<List<Product>> =
  productDataSource.getProducts(accessToken, storeId)


    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ): RepositoryResult<Nothing> =

        productDataSource.setProductState(accessToken, productId, isAvailable)


}