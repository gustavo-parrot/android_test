/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response;

import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.models.Availability

/**
 * ProductResponse
 *
 * @author (c) 2023, Umvel Inc.
 */
data class ProductResponse(
    val uuid: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Float,
    val availability: Availability,
    val category: CategoryResponse
)

data class CategoryResponse(
    val uuid: String,
    val name: String,
    val sortPosition: Int
)
