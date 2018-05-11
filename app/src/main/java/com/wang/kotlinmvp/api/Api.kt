package com.wang.kotlinmvp.api

import android.os.Environment
import android.util.Log
import com.google.gson.GsonBuilder
import com.wang.kotlinmvp.app.MyApp
import com.wang.kotlinmvp.base.gson.GsonConverterFactory
import com.wang.kotlinmvp.base.utils.NetWorkUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Mis Wang
 * @date  2018/5/9  17:27
 * @fuction 网络初始化 Api
 */
class Api {
    private var retrofit: Retrofit? = null
    private var apiService: ApiService? = null

    init {
        //拦截器日志
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val cacheFile = File(HTTP_CACHE_PATH)
        val cache = Cache(cacheFile, 1024 * 1024 * 100)//100Mb
        val builder = OkHttpClient.Builder()
        with(builder) {
            writeTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            connectTimeout(20, TimeUnit.SECONDS)
            addInterceptor(headerInterceptor)
            addInterceptor(interceptor)
            addNetworkInterceptor(HttpCacheInterceptor())
            cache(cache)
        }
        val okHttpClient = builder.build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()
        retrofit = Retrofit.Builder().baseUrl("")
                //设置 gson 转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                //RxJava 适配器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).build()
        apiService = retrofit!!.create(ApiService::class.java)
    }

    //双重锁的检验,获取Api对象
    companion object {
        private val CACHE_ROOT_PATH = Environment.getExternalStorageDirectory().path + "/Android/kotlin/"
        private val HTTP_CACHE_PATH = CACHE_ROOT_PATH + "cache/"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Api()
        }
    }

    // 增加头部信息
    private var headerInterceptor: Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    private class HttpCacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response? {
            var request = chain.request()
            if (!NetWorkUtils.isNetConnected(MyApp.instance)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
                Log.d("Okhttp", "no network")
            }

            val originalResponse = chain.proceed(request)
            return when {
                NetWorkUtils.isNetConnected(MyApp.instance) -> {
                    // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    val cacheControl = request.cacheControl().toString()
                    originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma")
                            .build()
                }
                else -> originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200").removeHeader("Pragma")
                        .build()
            }
        }
    }
}