package com.suraj.gateway.routing;

import com.suraj.gateway.exception.RouteNotFoundException;
import com.suraj.gateway.loadbalancer.LoadBalancer;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.RouteMatch;
import com.suraj.gateway.models.ServiceInstance;
import com.suraj.gateway.registry.ServiceRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutingEngine {

    private final List<Route> routes;

    private final ServiceRegistry serviceRegistry;

    private final LoadBalancer loadBalancer;

    public RoutingEngine(
            List<Route> routes,
            ServiceRegistry serviceRegistry,
            LoadBalancer loadBalancer
    ) {
        this.routes = routes;
        this.serviceRegistry = serviceRegistry;
        this.loadBalancer = loadBalancer;
    }

    public RouteMatch route(
            Request request
    ) {

        for(Route route : routes) {

            if(
                    matchesRoute(
                            route.getPathPattern(),
                            request.getPath()
                    )
            ) {

                Map<String, String> vars =
                        extractPathVariables(
                                route.getPathPattern(),
                                request.getPath()
                        );

                Request updatedRequest =
                        request.toBuilder()
                                .pathVariables(vars)
                                .build();

                List<ServiceInstance> serviceInstances =
                        serviceRegistry.getServices(
                                route.getServiceName()
                        );

                ServiceInstance selectedInstance =
                        loadBalancer.select(
                                route.getServiceName(),
                                serviceInstances
                        );

                return new RouteMatch(
                        selectedInstance,
                        updatedRequest
                );
            }
        }

        throw new RouteNotFoundException(
                request.getPath()
        );
    }

    private boolean matchesRoute(
            String routePattern,
            String requestPath
    ) {

        String[] routeParts =
                routePattern.split("/");

        String[] requestParts =
                requestPath.split("/");

        if(routeParts.length != requestParts.length) {
            return false;
        }

        for(int i=0;i<routeParts.length;i++) {

            String routePart =
                    routeParts[i];

            String requestPart =
                    requestParts[i];

            boolean isVariable =
                    routePart.startsWith("{")
                            &&
                            routePart.endsWith("}");

            if(isVariable) {
                continue;
            }

            if(!routePart.equals(requestPart)) {
                return false;
            }
        }

        return true;
    }

    private Map<String, String> extractPathVariables(
            String routePattern,
            String requestPath
    ) {

        Map<String, String> pathVariables =
                new HashMap<>();

        String[] routeParts =
                routePattern.split("/");

        String[] requestParts =
                requestPath.split("/");

        for(int i=0;i<routeParts.length;i++) {

            String routePart =
                    routeParts[i];

            String requestPart =
                    requestParts[i];

            boolean isVariable =
                    routePart.startsWith("{")
                            &&
                            routePart.endsWith("}");

            if(isVariable) {

                String variableName =
                        routePart.substring(
                                1,
                                routePart.length()-1
                        );

                pathVariables.put(
                        variableName,
                        requestPart
                );
            }
        }

        return pathVariables;
    }
}