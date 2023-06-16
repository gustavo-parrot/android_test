/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.usecase.product;

import io.parrotsoftware.qatest.data.product.ProductRepository

/**
 * SetProductStateUseCase
 *
 * @author (c) 2023, Parrot Inc.
 */
class SetProductStateUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String, isAvailable: Boolean) =
        productRepository.setProductState(productId, isAvailable)
}