package com.suraj.gateway.routing;

import com.suraj.gateway.enums.HttpMethod;

public class Route {

    private final String pathPattern;

    private final String serviceName;

    public Route(String pathPattern, String serviceName) {
        this.pathPattern = pathPattern;
        this.serviceName = serviceName;
    }

    public String getPathPattern(){
        return pathPattern;
    }
    public String getServiceName(){
        return serviceName;
    }
}

