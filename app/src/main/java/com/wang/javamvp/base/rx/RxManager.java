package com.wang.javamvp.base.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:54
 * @fuction
 */
public class RxManager {
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 添加Disposable
     */
    public void add(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除绑定
     */
    public void dispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }
}
