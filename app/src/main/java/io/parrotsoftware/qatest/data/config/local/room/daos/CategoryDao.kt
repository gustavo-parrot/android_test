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
import io.parrotsoftware.qatest.data.config.local.room.entities.CategoryEntity

/**
 * CategoryDao
 *
 * @author (c) 2023, Parrot Inc.
 */
@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE id_category = :categoryId")
    suspend fun getCategoryById(categoryId: String): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(vararg categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}
