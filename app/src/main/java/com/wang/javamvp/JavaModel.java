package com.wang.javamvp;

import com.wang.javamvp.api.Api;
import com.wang.javamvp.base.model.BaseModel;
import com.wang.javamvp.base.rx.RxHelper;
import com.wang.javamvp.base.rx.RxObserver;
import com.wang.javamvp.bean.ResultsBean;

import java.util.List;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:44
 * @fuction
 */
class JavaModel implements BaseModel {
    /**
     * 请求数据
     */
    void getList(RxObserver<List<ResultsBean>> observer) {
        Api.getInstance()
                .service.getListData().compose(RxHelper.<List<ResultsBean>>handleResult()).subscribe(observer);
    }
}
