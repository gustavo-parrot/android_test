package io.parrotsoftware.qatest.data.repositories.impl

import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiProductAvailability
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.services.ParrotApiService
import io.parrotsoftware.qatest.data.domain.local.Product
import io.parrotsoftware.qatest.data.domain.local.ProductDao
import io.parrotsoftware.qatest.data.domain.CategoryNetworkEntity
import io.parrotsoftware.qatest.data.domain.ProductNetworkEntity
import io.parrotsoftware.qatest.data.domain.RepositoryError
import io.parrotsoftware.qatest.data.domain.RepositoryResult
import io.parrotsoftware.qatest.data.mapper.EntityMapper
import io.parrotsoftware.qatest.data.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val networkInteractor: NetworkInteractor,
    private val remoteDataSource: ParrotApiService,
    private val localDataSource: ProductDao
) : ProductRepository {

    override suspend fun getProducts(
        accessToken: String,
        storeId: String
    ): RepositoryResult<List<Product>> {
        val response = networkInteractor.safeApiCall {
            remoteDataSource.getProducts("Bearer $accessToken", storeId)
        }
        return withContext(Dispatchers.IO) {
            if (response.isError) {
                Timber.tag("getAll").d(localDataSource.getAll().toString())
                RepositoryResult(
                    localDataSource.getAll(),
                    RepositoryError(
                        code = response.requiredError.requiredErrorCode,
                        message = response.requiredError.requiredErrorMessage
                    )
                )
            } else {
                val products = response.requiredResult.results.map {
                    ProductNetworkEntity(
                        it.uuid,
                        it.name,
                        it.description,
                        it.imageUrl,
                        it.price,
                        it.availability == ApiProductAvailability.AVAILABLE,
                        CategoryNetworkEntity(
                            it.category.uuid,
                            it.category.name,
                            it.category.sortPosition
                        )
                    )
                }
                val listProduct = products.map {
                    EntityMapper.mapFromProductEntity(it)
                }
                Timber.tag("insercion").d(listProduct.toString())
                localDataSource.insert(listProduct)
                RepositoryResult(listProduct)
            }
        }
    }

    override suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ): RepositoryResult<Nothing> {
        val body = ApiUpdateProductRequest(
            if (isAvailable) ApiProductAvailability.AVAILABLE
            else ApiProductAvailability.UNAVAILABLE
        )

        val response = networkInteractor.safeApiCall {
            remoteDataSource.updateProduct(
                "Bearer $accessToken",
                productId,
                body
            )
        }

        if (response.isError)
            return RepositoryResult(
                errorCode = response.requiredError.requiredErrorCode,
                errorMessage = response.requiredError.requiredErrorMessage
            )

        return RepositoryResult()
    }
}