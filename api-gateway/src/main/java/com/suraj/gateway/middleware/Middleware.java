package com.suraj.gateway.middleware;

import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;

public interface Middleware {

    MiddlewareResult execute(Request request);

}