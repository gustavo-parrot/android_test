/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.store.datasources;

import io.parrotsoftware.qatest.data.models.Result
import io.parrotsoftware.qatest.domain.models.Store

/**
 * StoreRemoteDataSource
 *
 * @author (c) 2023, Parrot Inc.
 */
interface StoreRemoteDataSource {

    suspend fun getStore(): Result<Store>

}
