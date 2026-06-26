package com.suraj.gateway.middleware;

import com.suraj.gateway.enums.HttpStatus;
import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;

import java.util.List;

public class MiddlewareChain {

    private final List<Middleware> middlewares;

    public MiddlewareChain(List<Middleware> middlewares) {
        this.middlewares = middlewares;
    }

    public MiddlewareResult process(Request request) {

        for(Middleware middleware : middlewares) {

            MiddlewareResult result =
                    middleware.execute(request);

            if(!result.isSuccess()) {
                return result;
            }
        }

        return new MiddlewareResult(true, HttpStatus.OK, "All Middlewares Passed");
    }
}