/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.product;


import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiProductAvailability
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qa_network.services.ParrotApi
import io.parrotsoftware.qatest.data.config.remote.retrofit.ParrotApiService
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.models.Availability
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.request.ProductAvailabilityRequest
import io.parrotsoftware.qatest.data.implementations.product.mapper.ProductResponseListToProductListMapper
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.models.Result
import io.parrotsoftware.qatest.data.product.datasources.ProductRemoteDataSource
import io.parrotsoftware.qatest.domain.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ProductRemoteDataSourceImpl
 *
 * @author (c) 2023, Parrot Inc.
 */
class ProductRemoteDataSourceImpl(
    private val userManagerImpl: UserManagerImpl,
    private val networkInteractorImpl: NetworkInteractorImpl,
    private val productResponseListToProductListMapper: ProductResponseListToProductListMapper
) : ProductRemoteDataSource {
    override suspend fun getProducts(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkInteractorImpl.safeApiCall {
                    ParrotApi.service.getProducts(userManagerImpl.getAccess(), userManagerImpl.getStoreUuid())
                }
                Result.Success(productResponseListToProductListMapper.map(result.result!!.results))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun setProductState(
        productId: String,
        isAvailable: Boolean
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val body = ApiUpdateProductRequest(
                    if (isAvailable) ApiProductAvailability.AVAILABLE
                    else ApiProductAvailability.UNAVAILABLE
                )
                val result = networkInteractorImpl.safeApiCall {
                    ParrotApi.service.updateProduct(
                        userManagerImpl.getAccess(),
                        productId,
                        body
                    )
                }
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
