package com.suraj.gateway.exception;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(String path) {
        super(
                "No route found for path: " + path
        );
    }
}