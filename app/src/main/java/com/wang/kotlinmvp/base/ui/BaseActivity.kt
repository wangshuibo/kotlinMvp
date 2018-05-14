package com.wang.kotlinmvp.base.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.wang.kotlinmvp.R
import com.wang.kotlinmvp.base.presenter.BasePresenter

/**
 * @author Mis Wang
 * @date  2018/5/11  16:15
 * @fuction 统一Activity管理->基类 [getLayoutId] 布局 [initPresenter]初始化Presenter [initView] 初始化视图
 */
abstract class BaseActivity<out P : BasePresenter<*, *>> : AppCompatActivity() {
    private var mPresenter: P? = null
    protected var mContext: Context? = null
    private var imm: InputMethodManager? = null//软键盘管理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0)
            setContentView(this.getLayoutId())
        this.mContext = this
        this.mPresenter = initPresenter()
        this.initView()
    }


    /**
     * 通过class跳转的界面
     *
     * @param cls 类名
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
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.anim_splash_enter, R.anim.anim_splash_quit)
    }

    /**
     * finish动画
     */
    fun finishActivityAnim() {
        overridePendingTransition(0, R.anim.anim_custom_quit)
    }

    /**
     * 如果软键盘是打开的话，就隐藏
     */
    private fun hideSoftKeyBoard() {
        val rootView: View = currentFocus
        if (this.imm == null) {
            this.imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        }
        if (this.imm != null) {
            this.imm!!.hideSoftInputFromWindow(rootView.windowToken, 2)
        }
    }

    /**
     * finish方法
     */
    override fun finish() {
        super.finish()
        hideSoftKeyBoard()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivityAnim();
    }

    /**
     * 销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        this.imm = null
        if (mPresenter != null) {
            this.mPresenter!!.onDestroy()
            this.mPresenter = null
        }

    }

    /**
     * 检测Presenter是否初始化
     */
    protected fun getPresenter(): P? {
        return if (initPresenter() == null) throw  NullPointerException("Presenter uninitialized -->P层，未初始化")
        else this.mPresenter
    }

    //子类实现布局
    protected abstract fun getLayoutId(): Int

    //简单的页面无需实现mvp就不管此方法,完美兼容各个实际场景的变通
    protected abstract fun initPresenter(): P

    //初始化视图
    protected abstract fun initView()
}
