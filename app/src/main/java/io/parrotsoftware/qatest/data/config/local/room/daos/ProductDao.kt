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
import io.parrotsoftware.qatest.data.config.local.room.entities.ProductEntity

/**
 * ProductDao
 *
 * @author (c) 2023, Parrot Inc.
 */
@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE id_product = :productId")
    suspend fun getProductById(productId: String): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(vararg productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)
}
