package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.datasources.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject
constructor(private val productRemoteDataSource: ProductRemoteDataSource) {

suspend fun getProducts(accessToken: String, storeId: String): RepositoryResult<List<Product>> =
  productRemoteDataSource.getProducts(accessToken, storeId)


    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ): RepositoryResult<Nothing> =

        productRemoteDataSource.setProductState(accessToken, productId, isAvailable)


}