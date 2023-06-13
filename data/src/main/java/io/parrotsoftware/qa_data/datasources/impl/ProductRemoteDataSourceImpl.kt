package io.parrotsoftware.qa_data.datasources.impl

import io.parrotsoftware.qa_data.CategoryD
import io.parrotsoftware.qa_data.ProductD
import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.datasources.ProductRemoteDataSource
import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiProductAvailability
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.services.ParrotApi

class ProductRemoteDataSourceImpl (
       private val networkInteractor: NetworkInteractor
        ): ProductRemoteDataSource {

    override suspend fun getProducts(accessToken: String, storeId: String) : RepositoryResultD<List<ProductD>> {
        val response = networkInteractor.safeApiCall {
            ParrotApi.service.getProducts("Bearer $accessToken", storeId)
        }

        if (response.isError)
            return RepositoryResultD(
                errorCode = response.requiredError.requiredErrorCode,
                errorMessage = response.requiredError.requiredErrorMessage
            )

        val products = response.requiredResult.results.map {
            ProductD(
                it.uuid,
                it.name,
                it.description,
                it.imageUrl,
                it.price,
                it.availability == ApiProductAvailability.AVAILABLE,
                CategoryD(it.category.uuid, it.category.name, it.category.sortPosition)
            )
        }
        return RepositoryResultD(products)
    }

    override suspend fun setProductState(
        accessToken: String,
        productId: String,
        isAvailable: Boolean
    ) :  RepositoryResultD<Nothing>{

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
            return RepositoryResultD(
                errorCode = response.requiredError.requiredErrorCode,
                errorMessage = response.requiredError.requiredErrorMessage
            )

        return RepositoryResultD()

    }
}