/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.authentication;

import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationLocalDataSource
import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationRemoteDataSource

/**
 * AuthenticationRepository
 *
 * @author (c) 2023, Parrot Inc.
 */
class AuthenticationRepository(
    private val authenticationLocalDataSource: AuthenticationLocalDataSource,
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource
) {
    suspend fun login(email: String, password: String) =
        authenticationRemoteDataSource.login(email, password)

    suspend fun userExists() = authenticationRemoteDataSource.userExists()

    suspend fun getCredentials() = authenticationRemoteDataSource.getCredentials()
}
