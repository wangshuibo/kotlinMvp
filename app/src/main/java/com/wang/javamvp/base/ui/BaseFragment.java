package com.wang.javamvp.base.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.javamvp.base.presenter.BasePresenter;
import com.wang.kotlinmvp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:28
 * @fuction 统一管理Fragment -> 基类 [getLayoutResource] 布局 [initPresenter]初始化Presenter [initView] 初始化视图
 */
public abstract class BaseFragment<P extends BasePresenter<?, ?>> extends Fragment {
    protected View rootView = null;
    private P mPresenter;
    //fragment不可见
    private boolean isViewVisiable;
    //是否准备
    private boolean isPrepared;
    //是否加载数据
    private boolean isDataAdd;
    //非ViewPager
    private boolean isNoViewpager;

    private Unbinder mUnbinder = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResource(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        this.mPresenter = initPresenter();
        if (!isNoViewpager && !isViewVisiable && !isDataAdd) {
            load();
        }
//        val group = rootView!!.parent as ViewGroup
//        group.removeView(rootView)
        return rootView;
    }


    /**
     * 通过class跳转的界面
     *
     * @param cls 类名
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);

    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }


    /**
     * 初始化数据
     */
    private void load() {
        initView();
        isDataAdd = true;
    }

    /**
     * Viewpager调用
     *
     * @param isVisibleToUser 可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisiable = !getUserVisibleHint();
        if (!isViewVisiable && isPrepared && !isDataAdd) {
            load();
        }
    }

    /**
     * 非Viewpager调用
     *
     * @param hidden Fragment show或hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isNoViewpager = !hidden;
        if (isNoViewpager && isPrepared && !isDataAdd) {
            load();
        }
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        this.mPresenter = null;
        isPrepared = false;
        isViewVisiable = false;
        isDataAdd = false;
        isNoViewpager = false;
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    /**
     * 检测Presenter是否初始化
     */
    protected P getPresenter() {
        if (initPresenter() == null) {
            throw new NullPointerException("Presenter uninitialized -->P层，未初始化");
        } else
            return this.mPresenter;
    }


    // 获取布局文件
    protected abstract Integer getLayoutResource();

    // 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    abstract P initPresenter();

    // 初始化view
    protected abstract void initView();
}
