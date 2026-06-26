package com.suraj.gateway.controller;

import com.suraj.gateway.converter.RequestConverter;
import com.suraj.gateway.enums.HttpMethod;
import com.suraj.gateway.gateway.ApiGateway;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class GatewayController {

    private final ApiGateway apiGateway;

    private final RequestConverter requestConverter;

    public GatewayController(
            ApiGateway apiGateway,
            RequestConverter requestConverter
    ) {
        this.apiGateway = apiGateway;
        this.requestConverter = requestConverter;
    }

    @GetMapping("/users/{id}")
    public String getUser(
            HttpServletRequest servletRequest
    ) {

        Request request =
                requestConverter.convert(
                        servletRequest
                );

        Response response =
                apiGateway.handle(
                        request
                );

        return response.getBody();
    }
}