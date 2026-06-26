package com.suraj.gateway.security;

public class JwtValidator {

    public boolean validate(String token) {

        return "valid-token".equals(token);
    }
}