package io.parrotsoftware.qa_data.datasources.impl

import io.parrotsoftware.qa_data.datasources.ProductDataSource
import io.parrotsoftware.qa_data.domain.Category
import io.parrotsoftware.qa_data.domain.Product
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.managers.ProductManager
import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiProductAvailability
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.services.ParrotApi

class ProductDataSourceImpl(
    private val networkInteractor: NetworkInteractor,
    private val productManager: ProductManager
) : ProductDataSource {

    override suspend fun getProducts(accessToken: String, storeId: String)
            : RepositoryResult<List<Product>> {
        val response = networkInteractor.safeApiCall {
            ParrotApi.service.getProducts("Bearer $accessToken", storeId)
        }

        if (response.isError) {
            //Check is there are data into database local
            val products = productManager.getProducts()

           return if (products.isEmpty()){
               RepositoryResult(
                   errorCode = response.requiredError.requiredErrorCode,
                   errorMessage = response.requiredError.requiredErrorMessage
               )
            }else {
               RepositoryResult(products)
            }

        }


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
        //save into local db
        productManager.saveProducts(products)

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

        if (response.isError){
            //update product locally
            productManager.updateProduct(productId,isAvailable)
            //TODO implement functionality to update remote
        }


        return RepositoryResult()

    }
}