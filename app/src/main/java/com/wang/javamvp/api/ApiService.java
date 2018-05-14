package com.wang.javamvp.api;

import com.wang.javamvp.base.bean.BaseBean;
import com.wang.javamvp.bean.ResultsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:44
 * @fuction
 */
public interface ApiService {
    public static final String BASE_URL = "http://gank.io/api/";


    @GET("data/Android/10/1")
    Observable<BaseBean<List<ResultsBean>>> getListData();

}
