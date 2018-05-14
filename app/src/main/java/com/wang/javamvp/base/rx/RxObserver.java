package com.wang.javamvp.base.rx;

import com.google.gson.JsonSyntaxException;
import com.wang.javamvp.base.rx.errorbean.ApiException;
import com.wang.javamvp.base.rx.errorbean.HttpException;
import com.wang.javamvp.base.utils.NetWorkUtils;
import com.wang.kotlinmvp.R;
import com.wang.kotlinmvp.app.MyApp;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:06
 * @fuction 通过Observable(可观察者, 被观察者)发送一个Observable，
 * 然后通过Observer(观察者)来响应获取数据
 */
public abstract class RxObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        rxSubscribe(d);
    }

    @Override
    public void onNext(T t) {
        rxNext(t);
    }

    @Override
    public void onError(Throwable e) {
        // 网络不可用
        if (!NetWorkUtils.isNetConnected(MyApp.Companion.getInstance())) {
            rxError("-1", MyApp.Companion.getInstance().getString(R.string.no_net));
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            rxError("-1", "请求超时,请稍后再试");
        } else if (e instanceof JsonSyntaxException) {//解析出错
            rxError("-1", "解析出错");
        } else if (e instanceof ApiException) {//返回code!=200
            ApiException apiException = (ApiException) e;
            rxError(apiException.getCode(), apiException.getErrorMsg());
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            rxError(httpException.getCode(), MyApp.Companion.getInstance().getString(R.string.service_error));
        } else {
            rxError("-1", MyApp.Companion.getInstance().getString(R.string.net_error));
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * @param d
     */
    protected abstract void rxSubscribe(Disposable d);

    /**
     * @param t 返回的值
     */
    protected abstract void rxNext(T t);

    /**
     * @param code    服务器返回的状态码
     * @param message 服务器返回当前状态的信息
     */
    protected abstract void rxError(String code, String message);
}
