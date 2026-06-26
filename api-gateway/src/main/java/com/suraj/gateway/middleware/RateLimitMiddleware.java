package com.suraj.gateway.middleware;

import com.suraj.gateway.enums.HttpStatus;
import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.Response;
import com.suraj.gateway.ratelimit.RateLimiter;

public class RateLimitMiddleware implements Middleware {

    private final RateLimiter rateLimiter;

    public RateLimitMiddleware(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public MiddlewareResult execute(Request request) {
        String ip = request.getIpAddress();
        boolean allowed = rateLimiter.allowRequest(ip);
        if(!allowed) {
            return new MiddlewareResult(
                    false,
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Too Many Requests"
            );
        }else{
            return new MiddlewareResult(
                    true,
                    HttpStatus.OK,
                    "Request Allowed"
            );
        }
    }
}

