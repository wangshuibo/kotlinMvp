package com.wang.javamvp.base.presenter;


import com.wang.javamvp.base.rx.RxManager;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:53
 * @fuction 基本Presenter型 ：是构建Model和View之间的桥梁。
 * 业务逻辑都在这里定义 [onDestroy]清除所有缓存->防止发生内存泄漏
 */
public class BasePresenter<V, M> {
    protected M mModel = null;
    protected V mView = null;
    protected RxManager mRxManager = new RxManager();

    protected void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    protected void onStart() {
    }

    public void onDestroy() {
        this.mModel = null;
        this.mView = null;
        this.mRxManager.dispose();
    }
}
