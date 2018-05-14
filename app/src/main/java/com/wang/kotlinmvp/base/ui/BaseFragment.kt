package com.wang.kotlinmvp.base.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wang.kotlinmvp.R
import com.wang.kotlinmvp.base.presenter.BasePresenter


/**
 * @author Mis Wang
 * @date  2018/5/11  17:00
 * @fuction 统一管理Fragment -> 基类 [getLayoutResource] 布局 [initPresenter]初始化Presenter [initView] 初始化视图
 */
abstract class BaseFragment<out P : BasePresenter<*, *>> : Fragment() {
    protected var rootView: View? = null
    private var mPresenter: P? = null
    //fragment不可见
    private var isViewVisiable = false
    //是否准备
    private var isPrepared = false
    //是否加载数据
    private var isDataAdd = false
    //非ViewPager
    private var isNoViewpager = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(getLayoutResource(), container, false)
        this.mPresenter = initPresenter()
        if (!isNoViewpager && !isViewVisiable && !isDataAdd) {
            load()
        }
//        val group = rootView!!.parent as ViewGroup
//        group.removeView(rootView)
        return rootView
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivity(cls: Class<*>) {
        startActivity(cls, null)
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(activity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
        activity!!.overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(activity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        activity!!.overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit)
    }

    /**
     * 初始化数据
     */
    private fun load() {
        initView()
        isDataAdd = true
    }

    /**
     * Viewpager调用
     *
     * @param isVisibleToUser 可见
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisiable = !userVisibleHint
        if (!isViewVisiable && isPrepared && !isDataAdd) {
            load()
        }
    }

    /**
     * 非Viewpager调用
     *
     * @param hidden Fragment show或hidden
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isNoViewpager = !hidden
        if (isNoViewpager && isPrepared && !isDataAdd) {
            load()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.onDestroy()
        this.mPresenter = null
        isPrepared = false
        isViewVisiable = false
        isDataAdd = false
        isNoViewpager = false
    }

    /**
     * 检测Presenter是否初始化
     */
    protected fun getPresenter(): P? {
        return if (initPresenter() == null) throw  NullPointerException("Presenter uninitialized -->P层，未初始化")
        else this.mPresenter
    }


    // 获取布局文件
    protected abstract fun getLayoutResource(): Int

    // 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    abstract fun initPresenter(): P

    // 初始化view
    protected abstract fun initView()
}