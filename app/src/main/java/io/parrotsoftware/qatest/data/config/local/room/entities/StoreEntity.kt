/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.local.room.entities;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * StoreEntity
 *
 * @author (c) 2023, Parrot Inc.
 */
@Entity(tableName = "store_table")
data class StoreEntity(
    @PrimaryKey
    @ColumnInfo("id_store")
    val idStore: String,
    val name: String
)
