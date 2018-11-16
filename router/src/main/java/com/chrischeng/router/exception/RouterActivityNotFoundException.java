package com.chrischeng.router.exception;

public class RouterActivityNotFoundException extends RuntimeException {

    public RouterActivityNotFoundException(String message) {
        super(String.format("Find rule by %s failed.", message));
    }
}
