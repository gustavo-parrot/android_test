package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.domain.RepositoryResult

interface ProductRemoteDataSource {

    suspend fun getProducts(accessToken: String, storeId: String)
    : RepositoryResult<List<Product>>

    suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ) : RepositoryResult<Nothing>

}