package io.parrotsoftware.qa_data.managers.impl

import io.parrotsoftware.qa_data.bd.AppDatabase
import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.managers.ProductManager
import io.parrotsoftware.qa_data.mappers.ProductMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductManagerImpl
@Inject constructor(
    private val database: AppDatabase,
    private val productMapper: ProductMapper
) : ProductManager {

    override suspend fun saveProducts(products: List<Product>) {
        withContext(Dispatchers.IO){
            database.productDao().insertAll(productMapper.toEntity(products))
        }
    }

    override suspend fun getProducts(): List<Product> =
        withContext(Dispatchers.IO){
            productMapper.toDomain(
                database.productDao().getAllProducts()
            )
        }

    override suspend fun updateProduct(productId: String, available: Boolean) {
        withContext(Dispatchers.IO){
            database.productDao().updateProduct(productId, available)
        }
    }


}


