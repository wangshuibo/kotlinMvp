package com.wang.kotlinmvp.base.rx.errorbean

import retrofit2.Response

/**
 * @author Mis Wang
 * @date  2018/5/11  14:28
 * @fuction
 */
/** Exception for an unexpected, non-2xx HTTP response.  */
class HttpException(@field:Transient private val response: Response<*>)
    : Exception("HTTP " + response.code() + " " + response.message()) {
    private val code: String = response.code().toString()
    private val messages: String = response.message()

    /** HTTP status code.  */
    fun code(): String {
        return code
    }

    /** HTTP status message.  */
    fun message(): String {
        return messages
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    fun response(): Response<*> {
        return response
    }
}
