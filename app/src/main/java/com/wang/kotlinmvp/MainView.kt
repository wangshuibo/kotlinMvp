package com.wang.kotlinmvp

import com.wang.kotlinmvp.base.view.BaseView
import com.wang.kotlinmvp.bean.ResultsBean

/**
 * @author Mis Wang
 * @date  2018/5/14  10:14
 * @fuction
 */
interface MainView : BaseView {
    fun getListData(datas: MutableList<ResultsBean>)
}