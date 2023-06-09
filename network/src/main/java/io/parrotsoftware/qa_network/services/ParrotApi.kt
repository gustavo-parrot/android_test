package io.parrotsoftware.qa_network.services

import io.parrotsoftware.qa_network.BuildConfig
import io.parrotsoftware.qa_network.NetworkBuilder
import io.parrotsoftware.qa_network.domain.requests.ApiAuthRequest
import io.parrotsoftware.qa_network.domain.requests.ApiUpdateProductRequest
import io.parrotsoftware.qa_network.domain.responses.ApiCredentials
import io.parrotsoftware.qa_network.domain.responses.ApiListResponse
import io.parrotsoftware.qa_network.domain.responses.ApiProduct
import io.parrotsoftware.qa_network.domain.responses.ApiSingleResponse
import io.parrotsoftware.qa_network.domain.responses.ApiUserWithStores
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ParrotApiService {

    @POST("/api/auth/token")
    suspend fun auth(
        @Body authRequest: ApiAuthRequest
    ): ApiCredentials

    @GET("/api/v1/users/me")
    suspend fun getMe(
        @Header("Authorization") access: String
    ): ApiSingleResponse<ApiUserWithStores>

    @GET("/api/v1/products/")
    suspend fun getProducts(
        @Header("Authorization") access: String,
        @Query("store") storeId: String
    ): ApiListResponse<ApiProduct>

    @PUT("/api/v1/products/{product_id}/availability")
    suspend fun updateProduct(
        @Header("Authorization") access: String,
        @Path("product_id") productId: String,
        @Body request: ApiUpdateProductRequest
    ): ApiSingleResponse<ApiProduct>
}

/*
object ParrotApi {

    val service: ParrotApiService by lazy {
        NetworkBuilder.build(BuildConfig.BASE_URL).create(ParrotApiService::class.java)
    }
}*/
