package com.wang.kotlinmvp.base.view

/**
 * @author Mis Wang
 * @date  2018/5/10  11:49
 * @fuction 基本View型 ：主要放所有视图逻辑的接口
 */
interface BaseView {
    /**
     * 内嵌加载
     * @param title 弹窗标题
     */
    fun showLoading(title: String)

    /**
     * 停止加载
     */
    fun stopLoading()

    /**
     * @param msg 错误信息
     */
    fun showErrorTip(msg: String)
}