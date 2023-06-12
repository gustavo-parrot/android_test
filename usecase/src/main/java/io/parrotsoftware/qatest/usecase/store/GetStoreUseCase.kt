/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.usecase.store

import io.parrotsoftware.qatest.data.store.StoreRepository

/**
 * GetStoreUseCase
 *
 * @author (c) 2023, Parrot Inc.
 */
class GetStoreUseCase(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke() = storeRepository.getStore()
}
