package com.suraj.gateway.converter;

import com.suraj.gateway.enums.HttpMethod;
import com.suraj.gateway.models.Request;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestConverter {

    public Request convert(
            HttpServletRequest servletRequest
    ) {

        Map<String, String> headers =
                new HashMap<>();

        servletRequest
                .getHeaderNames()
                .asIterator()
                .forEachRemaining(header ->

                        headers.put(
                                header,
                                servletRequest.getHeader(header)
                        )
                );

        Map<String, String> queryParams =
                new HashMap<>();

        servletRequest
                .getParameterMap()
                .forEach(
                        (key, value) ->
                                queryParams.put(
                                        key,
                                        value[0]
                                )
                );

        System.out.println("===== Headers received =====");

        headers.forEach((k, v) ->
                System.out.println(k + " : " + v)
        );

        return Request.builder()
                .method(
                        HttpMethod.valueOf(
                                servletRequest.getMethod()
                        )
                )
                .path(
                        servletRequest.getRequestURI()
                )
                .headers(headers)
                .queryParams(queryParams)
                .body("")
                .ipAddress(
                        servletRequest.getRemoteAddr()
                )
                .build();
    }
}