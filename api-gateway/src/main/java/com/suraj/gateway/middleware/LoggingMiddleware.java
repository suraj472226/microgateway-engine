package com.suraj.gateway.middleware;

import com.suraj.gateway.enums.HttpStatus;
import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;

public class LoggingMiddleware implements Middleware {

    @Override
    public MiddlewareResult execute(Request request) {

        System.out.println("========== LOGGINGMiddleware REQUEST ==========");
//        System.out.println("Method: " + request.getMethod());
//        System.out.println("Path: " + request.getPath());
//        System.out.println("IP Address: " + request.getIpAddress());
//        System.out.println("=============================");

        return new MiddlewareResult(
                true,
                HttpStatus.OK,
                "Request Logged Successfully"
        );
    }
}