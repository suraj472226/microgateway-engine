package com.suraj.gateway.models;

import com.suraj.gateway.enums.HttpStatus;

import java.util.Collections;
import java.util.Map;

public class Response {

    private final HttpStatus status;

    private final Map<String, String> headers;

    private final String body;

    public Response(
            HttpStatus status,
            Map<String, String> headers,
            String body
    ) {
        this.status = status;
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}