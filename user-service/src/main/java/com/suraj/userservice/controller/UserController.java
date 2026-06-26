package com.suraj.userservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public Map<String, Object> getUser(
            @PathVariable Long id
    ) {

        System.out.println(
                "User Service received request for user "
                        + id
        );

        return Map.of(
                "id", id,
                "name", "User-" + id,
                "service", "user-service"
        );
    }
}