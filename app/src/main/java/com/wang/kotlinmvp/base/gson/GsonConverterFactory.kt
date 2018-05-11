package com.wang.kotlinmvp.base.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author Mis Wang
 * @date  2018/5/11  14:43
 * @fuction
 */
class GsonConverterFactory private constructor(private val mGson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(mGson, adapter)
    }

    override fun requestBodyConverter(type: Type,
                                      parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>,
                                      retrofit: Retrofit): Converter<*, RequestBody> {
        val adapter = mGson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(mGson, adapter)
    }

    companion object {

        /**
         * Create an instance using a default [Gson] instance for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        fun create(): GsonConverterFactory {
            return create(Gson())
        }

        /**
         * Create an instance using `gson` for conversion. Encoding to JSON and
         * decoding from JSON (when no charset is specified by a header) will use UTF-8.
         */
        // Guarding public API nullability.
        fun create(gson: Gson?): GsonConverterFactory {
            if (null == gson) throw NullPointerException("gson == null")
            return GsonConverterFactory(gson)
        }
    }

}