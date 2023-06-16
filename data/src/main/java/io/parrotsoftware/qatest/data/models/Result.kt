/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.models;

import java.lang.Exception

/**
 * Result
 *
 * @author (c) 2023, Parrot Inc.
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()

    data class Error(val exception: Exception): Result<Nothing>()
}
