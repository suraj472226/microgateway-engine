package com.suraj.gateway.gateway;

import com.suraj.gateway.exception.NoHealthyServiceException;
import com.suraj.gateway.exception.RouteNotFoundException;
import com.suraj.gateway.forwarding.HttpForwarder;
import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.Response;
import com.suraj.gateway.middleware.MiddlewareChain;
import com.suraj.gateway.models.RouteMatch;
import com.suraj.gateway.routing.RoutingEngine;
import com.suraj.gateway.enums.HttpStatus;

import java.util.Map;

public class ApiGateway {

    private final MiddlewareChain middlewareChain;

    private final RoutingEngine routingEngine;

    private final HttpForwarder httpForwarder;

    public ApiGateway(
            MiddlewareChain middlewareChain,
            RoutingEngine routingEngine,
            HttpForwarder httpForwarder
    ) {
        this.middlewareChain = middlewareChain;
        this.routingEngine = routingEngine;
        this.httpForwarder = httpForwarder;
    }

    public Response handle(Request request) {

        MiddlewareResult middlewareResult =
                middlewareChain.process(request);

        if(!middlewareResult.isSuccess()) {

            return new Response(
                    middlewareResult.getStatus(),
                    Map.of(),
                    middlewareResult.getMessage()
            );
        }

        try {

            RouteMatch match =
                    routingEngine.route(request);

            return httpForwarder.forward(
                    match.getRequest(),
                    match.getServiceInstance()
            );

        } catch(RouteNotFoundException e) {

            return new Response(
                    HttpStatus.NOT_FOUND,
                    Map.of(),
                    e.getMessage()
            );

        } catch(NoHealthyServiceException e) {

            return new Response(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    Map.of(),
                    e.getMessage()
            );
        }
    }
}