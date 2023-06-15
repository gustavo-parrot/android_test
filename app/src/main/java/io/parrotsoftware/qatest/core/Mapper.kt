/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.core;

import io.parrotsoftware.qa_network.domain.responses.ApiProduct

/**
 * Mapper
 *
 * @author (c) 2023, Parrot Inc.
 */
interface Mapper<I,O> {
    fun map(input: List<ApiProduct>): O
}
