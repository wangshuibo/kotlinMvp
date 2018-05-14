package com.wang.kotlinmvp.api

import com.wang.kotlinmvp.base.bean.KotlinBean
import com.wang.kotlinmvp.bean.ResultsBean
import io.reactivex.Observable
import retrofit2.http.GET


/**
 * @author Mis Wang
 * @date  2018/5/9  17:42
 * @fuction 定义接口
 */

interface ApiService {
    //
    companion object {
        val BASE_URL = "http://gank.io/api/"
    }

    @GET("data/Android/10/1")
    fun getListData(): Observable<KotlinBean<MutableList<ResultsBean>>>

}