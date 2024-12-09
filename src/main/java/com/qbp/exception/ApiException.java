package com.qbp.exception;


/**
 * 自定义API异常
 */
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
