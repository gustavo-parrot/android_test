package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.ProductD
import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.datasources.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryD @Inject
constructor(private val productRemoteDataSource: ProductRemoteDataSource) {

suspend fun getProducts(accessToken: String, storeId: String): RepositoryResultD<List<ProductD>>  =
  productRemoteDataSource.getProducts(accessToken, storeId)


    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ): RepositoryResultD<Nothing>  =

        productRemoteDataSource.setProductState(accessToken, productId, isAvailable)


}