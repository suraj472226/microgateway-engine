package com.suraj.gateway.loadbalancer;

import com.suraj.gateway.models.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    ServiceInstance select(
            String serviceName,
            List<ServiceInstance> serviceInstances
    );
}