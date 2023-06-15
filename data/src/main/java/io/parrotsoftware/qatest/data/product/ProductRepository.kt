/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.product;

import io.parrotsoftware.qatest.data.product.datasources.ProductLocalDataSource
import io.parrotsoftware.qatest.data.product.datasources.ProductRemoteDataSource


/**
 * ProductRepository
 *
 * @author (c) 2023, Parrot Inc.
 */
class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) {
    suspend fun getProducts() = productRemoteDataSource.getProducts()

    suspend fun setProductState(productId: String, isAvailable: Boolean) =
        productRemoteDataSource.setProductState(productId, isAvailable)
}
