package com.wang.javamvp.base.rx.errorbean;

import retrofit2.Response;

/**
 * Exception for an unexpected, non-2xx HTTP response.
 */
@SuppressWarnings("serial")
public final class HttpException extends Exception {
    private final String code;
    private final String message;
    private final transient Response<?> response;

    public HttpException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message());
        this.code = String.valueOf(response.code());
        this.message = response.message();
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * HTTP status code.
     */
    public String code() {
        return code;
    }

    /**
     * HTTP status message.
     */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}
