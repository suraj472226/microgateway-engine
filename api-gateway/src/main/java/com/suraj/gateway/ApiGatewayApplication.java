package com.suraj.gateway;

import com.suraj.gateway.enums.HttpMethod;
import com.suraj.gateway.gateway.ApiGateway;
import com.suraj.gateway.loadbalancer.LoadBalancer;
import com.suraj.gateway.loadbalancer.RoundRobinLoadBalancer;
import com.suraj.gateway.middleware.*;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.Response;
import com.suraj.gateway.ratelimit.RateLimiter;
import com.suraj.gateway.registry.ServiceRegistry;
import com.suraj.gateway.routing.Route;
import com.suraj.gateway.routing.RoutingEngine;
import com.suraj.gateway.security.JwtValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                ApiGatewayApplication.class,
                args
        );
    }


}