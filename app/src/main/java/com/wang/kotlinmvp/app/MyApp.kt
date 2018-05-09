package com.wang.kotlinmvp.app

import android.app.Application
import kotlin.properties.Delegates

/**
 * @author Mis Wang
 * @date  2018/5/9  17:19
 * @fuction Application
 */
class MyApp : Application() {

    /**
     * 获取Application单例
     */
    companion object {
        var instance: MyApp by Delegates.notNull()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}