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
import io.parrotsoftware.qatest.data.config.local.room.entities.CredentialsEntity

/**
 * CredentialsDao
 *
 * @author (c) 2023, Parrot Inc.
 */
@Dao
interface CredentialsDao {
    @Query("SELECT * FROM credentials_table")
    suspend fun getCredentials(): List<CredentialsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredentials(vararg credentialsEntity: CredentialsEntity)

    @Delete
    suspend fun deleteCredentials(credentialsEntity: CredentialsEntity)

    @Update
    suspend fun updateCredentials(credentialsEntity: CredentialsEntity)
}
