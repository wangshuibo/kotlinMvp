package com.wang.kotlinmvp.base.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * @author Mis Wang
 * @date  2018/5/9  18:00
 * @fuction
 */
object NetWorkUtils {
    fun getConnectivityStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return null != activeNetwork && activeNetwork.isConnected
    }

    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    fun isNetConnected(paramContext: Context): Boolean {
        val i = false
        val localNetworkInfo = (paramContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return localNetworkInfo != null && localNetworkInfo.isAvailable
    }

    /**
     * 检测wifi是否连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return true
        }
        return false
    }

    /**
     * 检测3G是否连接
     */
    fun is3gConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return true
        }
        return false
    }


}