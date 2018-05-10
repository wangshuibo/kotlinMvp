package com.wang.kotlinmvp.base.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author Mis Wang
 * @date  2018/5/10  17:26
 * @fuction 用来统一管理Disposable
 */
class RxManager() {
    //声明Disposable容器
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()

    /**
     * 添加Disposable
     */
    fun add(disposable: Disposable) {
        mCompositeDisposable!!.add(disposable)
    }

    /**
     * 解除绑定
     */
    fun dispose() {
        mCompositeDisposable!!.dispose()
        mCompositeDisposable = null
    }
}