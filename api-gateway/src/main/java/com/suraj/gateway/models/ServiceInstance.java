package com.suraj.gateway.models;

public class ServiceInstance {

    private final String host;

    private final int port;

    public ServiceInstance(
            String host,
            int port
    ) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}