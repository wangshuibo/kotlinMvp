package com.wang.kotlinmvp.base.rx

import com.google.gson.JsonSyntaxException
import com.wang.kotlinmvp.R
import com.wang.kotlinmvp.app.MyApp
import com.wang.kotlinmvp.base.rx.errorbean.ApiException
import com.wang.kotlinmvp.base.rx.errorbean.HttpException
import com.wang.kotlinmvp.base.utils.ConstantUtil
import com.wang.kotlinmvp.base.utils.NetWorkUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException


/**
 * @author Mis Wang
 * @date  2018/5/11  10:56
 * @fuction 通过Observable(可观察者,被观察者)发送一个Observable，
 * 然后通过Observer(观察者)来响应获取数据
 */
abstract class RxObserver<T> : Observer<T> {
    override fun onNext(t: T) {
        rxNext(t)
    }

    override fun onError(e: Throwable) {
        // 网络不可用
        if (!NetWorkUtils.isNetConnected(MyApp.instance)) {
            rxError(ConstantUtil.SUCCESS, MyApp.instance.getString(R.string.no_net))
        } else if (e is SocketTimeoutException || e is ConnectException) {
            rxError(ConstantUtil.SUCCESS, "请求超时,请稍后再试")
        } else if (e is JsonSyntaxException) {//解析出错
            rxError(ConstantUtil.SUCCESS, "解析出错")
        } else if (e is ApiException) {//返回code!=200
            rxError(e.code!!, e.errorMsg!!)
        } else if (e is HttpException) {
            rxError(e.code(), MyApp.instance.getString(R.string.service_error))
        } else {
            rxError(ConstantUtil.SUCCESS, MyApp.instance.getString(R.string.net_error))
        }
    }


    override fun onSubscribe(d: Disposable) {
        rxSubscribe(d)
    }

    override fun onComplete() {

    }

    /**
     * @param d
     */
    protected abstract fun rxSubscribe(d: Disposable)

    /**
     * @param t 返回的值
     */
    protected abstract fun rxNext(t: T)

    /**
     *@param code 服务器返回的状态码
     * @param message 服务器返回当前状态的信息
     */
    protected abstract fun rxError(code: String, message: String)
}