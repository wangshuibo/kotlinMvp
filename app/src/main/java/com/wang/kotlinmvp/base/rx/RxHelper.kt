package com.wang.kotlinmvp.base.rx

import com.wang.kotlinmvp.base.bean.BaseBean
import com.wang.kotlinmvp.base.rx.errorbean.ApiException
import com.wang.kotlinmvp.base.utils.ConstantUtil
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Mis Wang
 * @date  2018/5/11  15:14
 * @fuction Observable转换器
 */
object RxHelper {

    fun <T> handleResult(): ObservableTransformer<BaseBean<T>, T> {
        return ObservableTransformer { upstream ->
            upstream.flatMap { result ->
                if (ConstantUtil.SUCCESS.equals(result.status.code)) {
                    createObservable(result.content);
                } else {
                    Observable.error(ApiException(result.status.code, result.status.message));
                }
            }.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 创建被观察者
     */
    private fun <T> createObservable(t: T): Observable<T> {
        return Observable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }
        })
    }
}

