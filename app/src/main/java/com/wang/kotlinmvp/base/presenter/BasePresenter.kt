package com.wang.kotlinmvp.base.presenter

import com.wang.kotlinmvp.base.rx.RxManager

/**
 * @author Mis Wang
 * @date  2018/5/10  11:57
 * @fuction  基本Presenter型 ：是构建Model和View之间的桥梁。
 * 业务逻辑都在这里定义 [onDestroy]清除所有缓存->防止发生内存泄漏
 */
abstract class BasePresenter<V, M> {
    protected var mModel: M? = null
    protected var mView: V? = null
    protected val mRxManager: RxManager = RxManager()
    protected fun setVM(v: V, m: M) {
        this.mView = v
        this.mModel = m
        this.onStart()
    }

    protected fun onStart() {}

    fun onDestroy() {
        this.mModel = null
        this.mView = null
        this.mRxManager.dispose()
    }
}