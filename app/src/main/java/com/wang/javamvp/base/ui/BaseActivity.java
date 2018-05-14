package com.wang.javamvp.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.wang.javamvp.base.presenter.BasePresenter;
import com.wang.kotlinmvp.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Mis Wang
 * @date 2018/5/14  14:15
 * @fuction 统一Activity管理->基类 {@link #getLayoutId()} 布局 {@link #initPresenter()}初始化Presenter {@link #initView()} 初始化视图
 */
public abstract class BaseActivity<P extends BasePresenter<?, ?>> extends AppCompatActivity {
    private P mPresenter = null;
    protected Context mContext = null;
    private InputMethodManager imm = null;//软键盘管理
    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0)
            setContentView(this.getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        this.mContext = this;
        this.mPresenter = initPresenter();
        this.initView();
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit);
    }

    /**
     * finish动画
     */
    protected void finishActivityAnim() {
        overridePendingTransition(0, R.anim.anim_custom_quit);
    }

    /**
     * 如果软键盘是打开的话，就隐藏
     */
    private void hideSoftKeyBoard() {
        if (getCurrentFocus() != null) {
            View rootView = getCurrentFocus();
            if (this.imm == null) {
                this.imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            if (this.imm != null) {
                this.imm.hideSoftInputFromWindow(rootView.getWindowToken(), 2);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivityAnim();
    }

    /**
     * finish方法
     */
    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        if (mPresenter != null) {
            this.mPresenter.onDestroy();
            this.mPresenter = null;
        }
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

    //子类实现布局
    protected abstract int getLayoutId();

    //简单的页面无需实现mvp就不管此方法,完美兼容各个实际场景的变通
    protected abstract P initPresenter();

    //初始化视图
    protected abstract void initView();
}
