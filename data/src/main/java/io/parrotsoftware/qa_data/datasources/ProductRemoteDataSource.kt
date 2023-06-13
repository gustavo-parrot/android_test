package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.ProductD
import io.parrotsoftware.qa_data.RepositoryResultD

interface ProductRemoteDataSource {

    suspend fun getProducts(accessToken: String, storeId: String) : RepositoryResultD<List<ProductD>>

    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ) :  RepositoryResultD<Nothing>

}