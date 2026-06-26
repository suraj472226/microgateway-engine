package com.suraj.gateway.exception;

public class NoHealthyServiceException
        extends RuntimeException {

    public NoHealthyServiceException(String serviceName) {
        super(
                "No healthy instances available for service: "
                        + serviceName
        );
    }
}