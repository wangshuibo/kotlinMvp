package com.wang.javamvp.base.view;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:50
 * @fuction 基本View型 ：主要放所有视图逻辑的接口
 */
public interface BaseView {
    /**
     * 内嵌加载
     *
     * @param title 弹窗标题
     */
    void showLoading(String title);

    /**
     * 停止加载
     */
    void stopLoading();

    /**
     * @param msg 错误信息
     */
    void showErrorTip(String code, String msg);
}
