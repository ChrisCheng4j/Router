package com.chrischeng.router.exception;

public class RouterInterceptException extends RuntimeException {

    public RouterInterceptException(String message) {
        super(String.format("Intercept by %s when routing.", message));
    }
}
