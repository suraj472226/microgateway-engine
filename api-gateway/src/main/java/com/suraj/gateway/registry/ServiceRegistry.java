package com.suraj.gateway.registry;

import com.suraj.gateway.models.ServiceInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRegistry {

    private final Map<String, List<ServiceInstance>>
            services = new HashMap<>();

    public void registerService(
            String serviceName,
            List<ServiceInstance> serviceInstances
    ) {
        services.put(
                serviceName,
                serviceInstances
        );
    }

    public List<ServiceInstance> getServices(
            String serviceName
    ) {

        if (!services.containsKey(serviceName)) {
            throw new IllegalArgumentException(
                    "Service not found: " + serviceName
            );
        }

        return services.get(serviceName);
    }
}