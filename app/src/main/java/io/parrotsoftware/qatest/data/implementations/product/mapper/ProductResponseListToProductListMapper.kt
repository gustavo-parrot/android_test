/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.product.mapper;

import io.parrotsoftware.qa_network.domain.responses.ApiProduct
import io.parrotsoftware.qatest.core.Mapper
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.models.Availability
import io.parrotsoftware.qatest.domain.models.Category
import io.parrotsoftware.qatest.domain.models.Product
import javax.inject.Inject

/**
 * ProductResponseListToProductListMapper
 *
 * @author (c) 2023, Umvel Inc.
 */
class ProductResponseListToProductListMapper @Inject constructor() :
    Mapper<List<ApiProduct>, List<Product>> {
    override fun map(input: List<ApiProduct>): List<Product> = input.map { _productResponse ->
        Product(
            _productResponse.uuid,
            _productResponse.name,
            _productResponse.description,
            _productResponse.imageUrl,
            _productResponse.price,
            _productResponse.availability.name == Availability.AVAILABLE.name,
            Category(
                _productResponse.category.uuid,
                _productResponse.category.name,
                _productResponse.category.sortPosition
            )
        )
    }
}
