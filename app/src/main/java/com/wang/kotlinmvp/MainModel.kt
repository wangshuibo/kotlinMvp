package com.wang.kotlinmvp

import com.wang.kotlinmvp.api.Api
import com.wang.kotlinmvp.base.model.BaseModel
import com.wang.kotlinmvp.base.rx.RxHelper
import com.wang.kotlinmvp.base.rx.RxObserver
import com.wang.kotlinmvp.bean.ResultsBean


/**
 * @author Mis Wang
 * @date  2018/5/14  10:15
 * @fuction
 */
class MainModel : BaseModel {
    /**
     * 请求数据
     */
    fun getList(observer: RxObserver<MutableList<ResultsBean>>) {
        Api.instance
                .apiService?.getListData()?.compose(RxHelper.handleResult())?.subscribe(observer)
    }
}




