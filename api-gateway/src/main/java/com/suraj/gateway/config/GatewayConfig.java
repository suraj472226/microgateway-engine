package com.suraj.gateway.config;

import com.suraj.gateway.forwarding.HttpForwarder;
import com.suraj.gateway.gateway.ApiGateway;
import com.suraj.gateway.loadbalancer.LoadBalancer;
import com.suraj.gateway.loadbalancer.RoundRobinLoadBalancer;
import com.suraj.gateway.middleware.AuthMiddleware;
import com.suraj.gateway.middleware.LoggingMiddleware;
import com.suraj.gateway.middleware.MiddlewareChain;
import com.suraj.gateway.middleware.RateLimitMiddleware;
import com.suraj.gateway.models.ServiceInstance;
import com.suraj.gateway.ratelimit.RateLimiter;
import com.suraj.gateway.registry.ServiceRegistry;
import com.suraj.gateway.routing.Route;
import com.suraj.gateway.routing.RoutingEngine;
import com.suraj.gateway.security.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {

    @Bean
    public ApiGateway apiGateway() {

        ServiceRegistry registry =
                new ServiceRegistry();

        registry.registerService(
                "user-service",
                List.of(
                        new ServiceInstance(
                                "localhost",
                                8081
                        )
                )
        );

        registry.registerService(
                "product-service",
                List.of(
                        new ServiceInstance(
                                "localhost",
                                8082
                        )
                )
        );

        Route userRoute =
                new Route(
                        "/users/{id}",
                        "user-service"
                );

        Route userListRoute =
                new Route(
                        "/users",
                        "user-service"
                );


        Route productRoute =
                new Route(
                        "/products/{id}",
                        "product-service"
                );


        LoadBalancer loadBalancer =
                new RoundRobinLoadBalancer();

        RoutingEngine routingEngine =
                new RoutingEngine(
                        List.of(
                                userRoute,
                                userListRoute,
                                productRoute
                        ),
                        registry,
                        loadBalancer
                );

        JwtValidator jwtValidator =
                new JwtValidator();

        RateLimiter rateLimiter =
                new RateLimiter();

        MiddlewareChain middlewareChain =
                new MiddlewareChain(
                        List.of(
                                new LoggingMiddleware(),
                                new AuthMiddleware(
                                        jwtValidator
                                ),
                                new RateLimitMiddleware(
                                        rateLimiter
                                )
                        )
                );



        HttpForwarder httpForwarder =
                new HttpForwarder();

        return new ApiGateway(
                middlewareChain,
                routingEngine,
                httpForwarder
        );
    }
}