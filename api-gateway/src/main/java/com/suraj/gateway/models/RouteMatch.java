package com.suraj.gateway.models;

public class RouteMatch {

    private final ServiceInstance serviceInstance;

    private final Request request;

    public RouteMatch(
            ServiceInstance serviceInstance,
            Request request
    ) {
        this.serviceInstance = serviceInstance;
        this.request = request;
    }

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public Request getRequest() {
        return request;
    }
}