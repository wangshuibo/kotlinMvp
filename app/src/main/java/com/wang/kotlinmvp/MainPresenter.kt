package com.wang.kotlinmvp

import com.wang.kotlinmvp.base.presenter.BasePresenter
import com.wang.kotlinmvp.base.rx.RxObserver
import com.wang.kotlinmvp.bean.ResultsBean
import io.reactivex.disposables.Disposable

/**
 * @author Mis Wang
 * @date  2018/5/14  10:13
 * @fuction
 */
class MainPresenter : BasePresenter<MainView, MainModel> {
    constructor(view: MainView) {
        setVM(view, MainModel())
    }

    fun getListDatas() {
        mModel!!.getList(object : RxObserver<MutableList<ResultsBean>>() {
            override fun rxNext(t: MutableList<ResultsBean>) {
                mView!!.getListData(t)
            }

            override fun rxError(code: String, message: String) {
                mView!!.showErrorTip(code, message)
            }

            override fun rxSubscribe(d: Disposable) {
                mRxManager.add(d)
            }

        })
    }
}