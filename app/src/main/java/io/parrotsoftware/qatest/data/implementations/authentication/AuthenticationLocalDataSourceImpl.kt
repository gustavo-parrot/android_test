/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.implementations.authentication;

import io.parrotsoftware.qatest.data.authentication.datasources.AuthenticationLocalDataSource
import io.parrotsoftware.qatest.data.config.local.room.daos.CredentialsDao

/**
 * AuthenticationLocalDataSourceImpl
 *
 * @author (c) 2023, Umvel Inc.
 */
class AuthenticationLocalDataSourceImpl(
    private val credentialsDao: CredentialsDao
): AuthenticationLocalDataSource {
}
