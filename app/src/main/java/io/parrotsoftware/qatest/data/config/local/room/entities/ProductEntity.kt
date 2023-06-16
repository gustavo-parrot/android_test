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
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ProductEntity
 *
 * @author (c) 2023, Parrot Inc.
 */
@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_product")
    val idProduct: String,
    val name: String,
    val description: String,
    val image: String,
    val price: Float,
    @ColumnInfo(name = "is_available")
    val isAvailable: Boolean,
    @Embedded val categoryEntity: CategoryEntity
)
