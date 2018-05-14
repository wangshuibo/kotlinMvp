package com.wang.javamvp.base.bean;

/**
 * @author Mis Wang
 * @date 2018/5/14  13:47
 * @fuction
 */
public class BaseBean<T> {
    private T results;
    private boolean error;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
