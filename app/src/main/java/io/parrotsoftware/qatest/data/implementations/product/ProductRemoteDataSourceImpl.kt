/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.product;


import io.parrotsoftware.qa_network.domain.NetworkErrorType
import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiProductAvailability
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qa_network.services.ParrotApi
import io.parrotsoftware.qatest.data.config.local.room.daos.ProductDao
import io.parrotsoftware.qatest.data.config.local.room.entities.CategoryEntity
import io.parrotsoftware.qatest.data.config.local.room.entities.ProductEntity
import io.parrotsoftware.qatest.data.config.remote.retrofit.ParrotApiService
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.models.Availability
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.request.ProductAvailabilityRequest
import io.parrotsoftware.qatest.data.implementations.product.mapper.ProductEntityListToProductListMapper
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
    private val productDao: ProductDao,
    private val productResponseListToProductListMapper: ProductResponseListToProductListMapper,
    private val productEntityListToProductListMapper: ProductEntityListToProductListMapper
) : ProductRemoteDataSource {
    override suspend fun getProducts(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkInteractorImpl.safeApiCall {
                    ParrotApi.service.getProducts(
                        userManagerImpl.getAccess(),
                        userManagerImpl.getStoreUuid()
                    )
                }
                if (result.networkError?.errorCode.toString() == NetworkErrorType.CONNECTION_ERROR.toString()) {
                    Result.Success(productEntityListToProductListMapper.map(productDao.getProducts()))
                } else {
                    productDao.deleteAllProducts()
                    result.result!!.results.map { _product ->
                        productDao.insertProduct(
                            ProductEntity(
                                _product.uuid,
                                _product.name,
                                _product.description,
                                _product.imageUrl,
                                _product.price,
                                _product.availability.name == Availability.AVAILABLE.name,
                                CategoryEntity(
                                    _product.category.uuid,
                                    _product.category.name,
                                    _product.category.sortPosition
                                )
                            )
                        )
                    }
                    Result.Success(productResponseListToProductListMapper.map(result.result!!.results))
                }
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
