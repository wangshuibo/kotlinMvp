package com.wang.kotlinmvp.base.rx.errorbean

/**
 * @author Mis Wang
 * @date  2018/5/11  14:17
 * @fuction 运行时异常捕获
 */


/**
 * @param code 服务器返回的状态码
 * @param errorMsg 服务其返回的信息
 */
class ApiException(var code: String?, var errorMsg: String?) : RuntimeException(errorMsg)
