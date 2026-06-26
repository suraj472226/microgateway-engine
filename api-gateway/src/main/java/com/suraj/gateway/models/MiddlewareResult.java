package com.suraj.gateway.models;

import com.suraj.gateway.enums.HttpStatus;

public class MiddlewareResult {

    private final boolean success;

    private final HttpStatus status;

    private final String message;

    public MiddlewareResult(
            boolean success,
            HttpStatus status,
            String message
    ) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}