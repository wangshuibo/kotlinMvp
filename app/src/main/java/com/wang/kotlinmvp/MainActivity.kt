package com.wang.kotlinmvp

import com.wang.javamvp.JavaActivity
import com.wang.kotlinmvp.base.ui.BaseActivity
import com.wang.kotlinmvp.bean.ResultsBean
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun initView() {
        //kotlinMvp示例
        btn_kotlin!!.setOnClickListener {
            startActivity(KotlinActivity::class.java)
        }
        //JavaMvp示例
        btn_java!!.setOnClickListener {
            startActivity(JavaActivity::class.java)
        }
    }

    override fun showLoading(title: String) {
    }

    override fun stopLoading() {
    }

    override fun showErrorTip(code: String, msg: String) {
    }

    override fun getListData(datas: MutableList<ResultsBean>) {
    }

}

