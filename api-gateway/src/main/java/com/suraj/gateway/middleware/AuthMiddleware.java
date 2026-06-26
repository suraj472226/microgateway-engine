package com.suraj.gateway.middleware;

import com.suraj.gateway.enums.HttpStatus;
import com.suraj.gateway.models.MiddlewareResult;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.security.JwtValidator;

import java.util.Map;

public class AuthMiddleware implements Middleware {

    private final JwtValidator jwtValidator;

    public AuthMiddleware(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public MiddlewareResult execute(Request request) {

        String token = null;

        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {

            if (entry.getKey().equalsIgnoreCase("Authorization")) {
                token = entry.getValue();
                break;
            }
        }

        if(token == null) {
            return new MiddlewareResult(
                    false,
                    HttpStatus.UNAUTHORIZED,
                    "Authorization Header Missing"
            );
        }

        boolean valid =
                jwtValidator.validate(token);

        if(!valid) {
            return new MiddlewareResult(
                    false,
                    HttpStatus.UNAUTHORIZED,
                    "Invalid JWT Token"
            );
        }

        return new MiddlewareResult(
                true,
                HttpStatus.OK,
                "Authentication Successful"
        );
    }
}