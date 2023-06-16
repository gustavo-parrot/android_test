/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.product.mapper;

import io.parrotsoftware.qatest.core.Mapper
import io.parrotsoftware.qatest.data.config.local.room.entities.ProductEntity
import io.parrotsoftware.qatest.domain.models.Category
import io.parrotsoftware.qatest.domain.models.Product
import javax.inject.Inject

/**
 * ProductEntityListToProductListMapper
 *
 * @author (c) 2023, Umvel Inc.
 */
class ProductEntityListToProductListMapper @Inject constructor() :
    Mapper<List<ProductEntity>, List<Product>> {
    override fun map(input: List<ProductEntity>): List<Product> = input.map { _productEntity ->
        Product(
            _productEntity.idProduct,
            _productEntity.name,
            _productEntity.description,
            _productEntity.image,
            _productEntity.price,
            _productEntity.isAvailable,
            Category(
                _productEntity.categoryEntity.idCategory,
                _productEntity.categoryEntity.nameCategory,
                _productEntity.categoryEntity.positionCategory
            )
        )
    }
}
