/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.authentication;

import io.parrotsoftware.qa_network.domain.requests.ApiAuthRequest
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.interactors.impl.NetworkInteractorImpl
import io.parrotsoftware.qa_network.services.ParrotApi
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationRemoteDataSource
import io.parrotsoftware.qatest.data.config.remote.retrofit.ParrotApiService
import io.parrotsoftware.qatest.data.config.remote.retrofit.dto.request.LoginRequest
import io.parrotsoftware.qatest.data.managers.impl.UserManagerImpl
import io.parrotsoftware.qatest.data.models.Result
import io.parrotsoftware.qatest.domain.models.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * AuthenticationRemoteDataSourceImpl
 *
 * @author (c) 2023, Umvel Inc.
 */
class AuthenticationRemoteDataSourceImpl(
    private val userManagerImpl: UserManagerImpl,
    private val networkInteractorImpl: NetworkInteractorImpl,
) : AuthenticationRemoteDataSource {
    override suspend fun login(email: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val responseAuth = networkInteractorImpl.safeApiCall {
                    ParrotApi.service.auth(ApiAuthRequest(email, password))
                }
                val token = responseAuth.result!!.accessToken
                userManagerImpl.saveCredentials("Bearer $token", responseAuth.result!!.refreshToken)
                Result.Success(responseAuth.result!!.accessToken)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun userExists(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val responseUser = networkInteractorImpl.safeApiCall {
                    ParrotApi.service.getMe(userManagerImpl.getAccess())
                }

                val store = responseUser.result?.result?.stores?.first()
                userManagerImpl.saveStore(store!!.uuid, store.name)
                Result.Success(responseUser.result!!.result.email.isNotEmpty())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}
