package com.suraj.gateway.loadbalancer;

import com.suraj.gateway.models.ServiceInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundRobinLoadBalancer
        implements LoadBalancer {

    private final Map<String, Integer>
            currentIndexes = new HashMap<>();

    @Override
    public ServiceInstance select(
            String serviceName,
            List<ServiceInstance> serviceInstances
    ) {

        if (serviceInstances == null
                || serviceInstances.isEmpty()) {

            throw new IllegalArgumentException(
                    "No service instances available for "
                            + serviceName
            );
        }

        int currentIndex =
                currentIndexes.getOrDefault(
                        serviceName,
                        0
                );

        ServiceInstance selectedInstance =
                serviceInstances.get(currentIndex);

        currentIndexes.put(
                serviceName,
                (currentIndex + 1)
                        % serviceInstances.size()
        );

        return selectedInstance;
    }
}