package com.wang.javamvp.base.rx;

import com.wang.javamvp.base.bean.BaseBean;
import com.wang.javamvp.base.rx.errorbean.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:02
 * @fuction Observable转换器
 */
public class RxHelper {

    public static <T> ObservableTransformer<BaseBean<T>, T> handleResult() {
        return new ObservableTransformer<BaseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseBean<T>> upstream) {
                return upstream.flatMap(
                        new Function<BaseBean<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseBean<T> tBaseBean) throws Exception {
                                if (!tBaseBean.isError()) {
                                    return createObservable(tBaseBean.getResults());
                                } else {
                                    return Observable.error(new ApiException("-1", "错误数据"));
                                }
                            }
                        }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    private static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
