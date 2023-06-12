package io.parrotsoftware.qatest.data.repositories.impl

import io.parrotsoftware.qatest.data.domain.Product
import io.parrotsoftware.qatest.data.domain.RepositoryResult
import io.parrotsoftware.qatest.data.repositories.ProductRepository

class ProductRepositoryImpl(
   // private val networkInteractor: NetworkInteractor
) : ProductRepository {

    /*override suspend fun getProducts(
        accessToken: String,
        storeId: String
    ): RepositoryResult<List<Product>> {
        val response = networkInteractor.safeApiCall {
            ParrotApi.service.getProducts("Bearer $accessToken", storeId)
        }

        if (response.isError)
            return RepositoryResult(
                errorCode = response.requiredError.requiredErrorCode,
                errorMessage = response.requiredError.requiredErrorMessage
            )

        val products = response.requiredResult.results.map {
            Product(
                it.uuid,
                it.name,
                it.description,
                it.imageUrl,
                it.price,
                it.availability == ApiProductAvailability.AVAILABLE,
                Category(it.category.uuid, it.category.name, it.category.sortPosition)
            )
        }
        return RepositoryResult(products)
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
            ParrotApi.service.updateProduct(
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
    }*/
}