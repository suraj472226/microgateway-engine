package com.suraj.gateway.forwarding;

import com.suraj.gateway.enums.HttpMethod;
import com.suraj.gateway.enums.HttpStatus;
import com.suraj.gateway.models.Request;
import com.suraj.gateway.models.Response;
import com.suraj.gateway.models.ServiceInstance;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpForwarder {

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public Response forward(
            Request request,
            ServiceInstance serviceInstance
    ) {

        try {

            String url =
                    "http://"
                            + serviceInstance.getHost()
                            + ":"
                            + serviceInstance.getPort()
                            + request.getPath();

            HttpRequest.Builder builder =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(url)
                            );

            // Forward Headers
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {

                String header =
                        entry.getKey();

                if (header.equalsIgnoreCase("host")
                                ||
                                header.equalsIgnoreCase("connection")
                                ||
                                header.equalsIgnoreCase("content-length")
                ) {
                    continue;
                }

                builder.header(
                        header,
                        entry.getValue()
                );
            }

            // HTTP Method

            if(request.getMethod() == HttpMethod.GET) {

                builder.GET();

            } else if(request.getMethod() == HttpMethod.POST) {

                builder.POST(
                        HttpRequest.BodyPublishers.ofString(
                                request.getBody()
                        )
                );

            } else if(request.getMethod() == HttpMethod.PUT) {

                builder.PUT(
                        HttpRequest.BodyPublishers.ofString(
                                request.getBody()
                        )
                );

            } else if(request.getMethod() == HttpMethod.DELETE) {

                builder.DELETE();

            }

            HttpResponse<String> httpResponse =
                    httpClient.send(
                            builder.build(),
                            HttpResponse.BodyHandlers.ofString()
                    );

            return new Response(
                    HttpStatus.OK,
                    Map.of(),
                    httpResponse.body()
            );

        }
        catch (IOException | InterruptedException e) {

            throw new RuntimeException(e);
        }
    }
}