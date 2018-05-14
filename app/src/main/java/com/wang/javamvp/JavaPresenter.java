package com.wang.javamvp;

import com.wang.javamvp.base.presenter.BasePresenter;
import com.wang.javamvp.base.rx.RxObserver;
import com.wang.javamvp.bean.ResultsBean;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:41
 * @fuction
 */
class JavaPresenter extends BasePresenter<JavaView, JavaModel> {
    public JavaPresenter(JavaView view) {
        setVM(view, new JavaModel());
    }

    public void getListDatas() {
        mModel.getList(new RxObserver<List<ResultsBean>>() {

            @Override
            protected void rxSubscribe(Disposable d) {
                mRxManager.add(d);
            }

            @Override
            protected void rxNext(List<ResultsBean> resultsBeans) {
                mView.getListData(resultsBeans);
            }

            @Override
            protected void rxError(String code, String message) {
                mView.showErrorTip(code, message);
            }
        });
    }
}