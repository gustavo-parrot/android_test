/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.local.room;

import androidx.room.Database
import androidx.room.RoomDatabase
import io.parrotsoftware.qatest.data.config.local.room.daos.CategoryDao
import io.parrotsoftware.qatest.data.config.local.room.daos.CredentialsDao
import io.parrotsoftware.qatest.data.config.local.room.daos.ProductDao
import io.parrotsoftware.qatest.data.config.local.room.daos.StoreDao
import io.parrotsoftware.qatest.data.config.local.room.entities.CategoryEntity
import io.parrotsoftware.qatest.data.config.local.room.entities.CredentialsEntity
import io.parrotsoftware.qatest.data.config.local.room.entities.ProductEntity
import io.parrotsoftware.qatest.data.config.local.room.entities.StoreEntity

/**
 * ParrotDataBase
 *
 * @author (c) 2023, Parrot Inc.
 */
@Database(
    entities = [CategoryEntity::class, CredentialsEntity::class, ProductEntity::class, StoreEntity::class],
    version = ParrotDataBase.DB_VERSION,
    exportSchema = false
)
abstract class ParrotDataBase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun credentialsDao(): CredentialsDao
    abstract fun productDao(): ProductDao
    abstract fun storeDao(): StoreDao

    companion object {
        const val DB_VERSION = 1
    }
}
