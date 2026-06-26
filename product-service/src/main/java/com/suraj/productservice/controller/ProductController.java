package com.suraj.productservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public Map<String, Object> getUser(
            @PathVariable Long id
    ) {

        System.out.println(
                "Product Service received request for user "
                        + id
        );

        return Map.of(
                "id", id,
                "name", "User-" + id,
                "service", "product-service"
        );
    }
}