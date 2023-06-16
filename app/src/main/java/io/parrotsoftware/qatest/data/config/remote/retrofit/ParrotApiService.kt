/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.remote.retrofit;

import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.request.LoginRequest
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.request.ProductAvailabilityRequest
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response.CredentialsResponse
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response.ProductResponse
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response.ResponseListWrapper
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response.ResponseWrapper
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.response.UserWithStoresResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ParrotApiService
 *
 * @author (c) 2023, Umvel Inc.
 */
interface ParrotApiService {
    @POST("/api/auth/token")
    fun authAsync(
        @Body loginRequest: LoginRequest
    ): Deferred<CredentialsResponse>

    @GET("/api/v1/users/me")
    fun getMeAsync(
        @Header("Authorization") access: String
    ): Deferred<ResponseWrapper<UserWithStoresResponse>>

    @GET("/api/v1/products/")
    fun getProductsAsync(
        @Header("Authorization") access: String,
        @Query("store") storeId: String
    ): Deferred<ResponseListWrapper<ProductResponse>>

    @PUT("/api/v1/products/{product_id}/availability")
    fun updateProductAsync(
        @Header("Authorization") access: String,
        @Path("product_id") productId: String,
        @Body request: ProductAvailabilityRequest
    ): Deferred<ResponseWrapper<ProductResponse>>
}
