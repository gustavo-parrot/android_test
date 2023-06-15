/*
 * Copyright (c) 2023 Umvel Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Umvel Inc. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with Umvel Inc.
 */

package io.parrotsoftware.qatest.data.config.remote.retrofit.converterfactory;

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * NullOnEmptyConverterFactory
 *
 * @author (c) 2023, Umvel Inc.
 */
class NullOnEmptyConverterFactory private constructor() : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
        return Converter<ResponseBody, Any> { body ->
            if (body.contentLength() == 0L || body.contentType() == null) {
                null
            } else {
                delegate.convert(body)
            }
        }
    }

    companion object {
        fun create(): NullOnEmptyConverterFactory {
            return NullOnEmptyConverterFactory()
        }
    }
}
