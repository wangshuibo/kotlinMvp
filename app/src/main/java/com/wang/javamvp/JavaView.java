package com.wang.javamvp;

import com.wang.javamvp.base.view.BaseView;
import com.wang.javamvp.bean.ResultsBean;

import java.util.List;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:43
 * @fuction
 */
public interface JavaView extends BaseView {

    void getListData(List<ResultsBean> datas);
}
