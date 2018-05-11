package com.wang.kotlinmvp.base.gson

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.wang.kotlinmvp.base.bean.BaseBean
import com.wang.kotlinmvp.base.rx.errorbean.ApiException
import com.wang.kotlinmvp.base.utils.ConstantUtil
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset


/**
 * @author Mis Wang
 * @date  2018/5/11  14:46
 * @fuction Response的数据再做处理
 */
internal class GsonResponseBodyConverter<T>(private val mGson: Gson, private val adapter: TypeAdapter<T>)
    : Converter<ResponseBody, T> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val response = value.string()
        val baseBean = mGson.fromJson(response, BaseBean::class.java)
        // 关注的重点,自定义响应码中非0000的情况,一律抛出ApiException异常
        // 这样,我们就成功的将该异常交给onError()去处理了
        if (ConstantUtil.SUCCESS != baseBean.status.code) {
            Log.e("ApiError==", baseBean.status.message + "==")
            value.close()
            throw ApiException(baseBean.status.code, baseBean.status.message)
        } else {
            val mediaType = value.contentType()
            val charset = if (mediaType != null) mediaType.charset(UTF_8) else UTF_8
            val bis = ByteArrayInputStream(response.toByteArray())
            val reader = InputStreamReader(bis, charset)
            val jsonReader = mGson.newJsonReader(reader)
            value.use { _ ->
                return adapter.read(jsonReader)
            }
        }
    }

    companion object {
        private val UTF_8 = Charset.forName("UTF-8")
    }


}