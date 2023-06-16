/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.local.room.daos;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.parrotsoftware.qatest.data.config.local.room.entities.StoreEntity

/**
 * StoreDao
 *
 * @author (c) 2023, Parrot Inc.
 */
@Dao
interface StoreDao {
    @Query("SELECT * FROM store_table")
    suspend fun getStores(): List<StoreEntity>

    @Query("SELECT * FROM store_table WHERE id_store = :storeId")
    suspend fun getStoreById(storeId: String): StoreEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(vararg storeEntity: StoreEntity)

    @Delete
    suspend fun deleteStore(storeEntity: StoreEntity)

    @Update
    suspend fun updateStore(storeEntity: StoreEntity)
}
