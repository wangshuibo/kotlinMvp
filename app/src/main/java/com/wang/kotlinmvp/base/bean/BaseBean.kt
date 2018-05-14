package com.wang.kotlinmvp.base.bean


/**
 * @author Mis Wang
 * @date  2018/5/11  14:49
 * @fuction 基本结构Bean [StatusBean] code表示服务器返回的状态码 [StatusBean] message表示 服务器返回的信息 [pack_no] 表示报文 [t] 表示data里面的数据
 */
data class BaseBean<out T>(val status: StatusBean, private val pack_no: String, val data: T)

data class StatusBean(val code: String, val message: String)

data class KotlinBean<out T>(val error: Boolean, val results: T)