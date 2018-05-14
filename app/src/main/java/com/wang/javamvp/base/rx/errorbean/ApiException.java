package com.wang.javamvp.base.rx.errorbean;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:59
 * @fuction
 */
public class ApiException extends RuntimeException {
    private String code;
    private String errorMsg;

    public ApiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.errorMsg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
