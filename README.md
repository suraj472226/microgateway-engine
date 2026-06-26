# MicroGateway Engine

A lightweight API Gateway built **from scratch in Java and Spring Boot** to understand the internal architecture of modern API Gateways such as Spring Cloud Gateway, Kong, NGINX, and Envoy.

Instead of relying on existing gateway frameworks, this project implements the core gateway components manually, including routing, middleware execution, load balancing, service registry, and HTTP request forwarding.

---

## Motivation

Most developers use API Gateway frameworks without understanding what happens internally.

The goal of this project was to design and implement those concepts from scratch while following clean architecture and SOLID principles.

---

## Features

- Request Routing
- Dynamic Path Variable Matching
- Middleware Chain
- Authentication Middleware
- Rate Limiting
- Logging Middleware
- Service Registry
- Round Robin Load Balancer
- HTTP Request Forwarding
- Multiple Spring Boot Microservices
- Request Conversion Layer
- Clean Modular Architecture

---

## Project Structure

```
microgateway-engine
│
├── api-gateway
│   ├── controller
│   ├── converter
│   ├── forwarding
│   ├── gateway
│   ├── loadbalancer
│   ├── middleware
│   ├── models
│   ├── ratelimit
│   ├── registry
│   ├── routing
│   ├── security
│   └── config
│
├── user-service
│
└── product-service
```

---

# Architecture

```
                    Browser / Postman
                            │
                            ▼
                  API Gateway (8080)
                            │
                            ▼
                   Gateway Controller
                            │
                            ▼
                   Request Converter
                            │
                            ▼
                     Request Model
                            │
                            ▼
                       ApiGateway
          ┌─────────────────┴──────────────────┐
          ▼                                    ▼
    Middleware Chain                    Routing Engine
          │                                    │
          ▼                                    ▼
 Authentication                     Service Registry
 Logging                             Route Matching
 Rate Limiting                             │
                                           ▼
                                 Round Robin Load Balancer
                                           │
                                           ▼
                                   Service Instance
                                           │
                                           ▼
                                     HTTP Forwarder
                                           │
               ┌───────────────────────────┴──────────────────────────┐
               ▼                                                      ▼
        User Service (8081)                                  Product Service (8082)
```

---

# Request Flow

Example Request

```
GET /users/32
```

Flow

```
Browser

↓

Gateway Controller

↓

Request Converter

↓

Middleware Chain

↓

Routing Engine

↓

Service Registry

↓

Round Robin Load Balancer

↓

HTTP Forwarder

↓

User Service

↓

JSON Response

↓

Gateway

↓

Browser
```

---

# Implemented Components

## Request Converter

Converts incoming `HttpServletRequest` into the internal immutable Request model.

Responsibilities:

- Read HTTP Method
- Read URI
- Read Headers
- Read Query Parameters
- Read Client IP

---

## Middleware Chain

Implements the Chain of Responsibility pattern.

Current middlewares:

- Logging
- Authentication
- Rate Limiting

Each middleware is independently extensible.

---

## Routing Engine

Matches incoming requests against configured routes.

Supports:

- Static Routes
- Path Variables

Example:

```
/users/{id}
```

matches

```
/users/32
```

---

## Service Registry

Maintains all available service instances.

Example

```
user-service

↓

localhost:8081
```

---

## Round Robin Load Balancer

Distributes requests among available service instances.

Example

```
Request 1 → Instance A

Request 2 → Instance B

Request 3 → Instance C

Request 4 → Instance A
```

---

## HTTP Forwarder

Forwards incoming HTTP requests to the selected microservice.

Responsibilities:

- Build target URL
- Forward headers
- Forward HTTP method
- Return downstream response

---

# Technologies Used

- Java 21
- Spring Boot
- Maven
- Java HTTP Client
- REST APIs

---

# Design Principles

The project follows several software engineering principles.

- SOLID Principles
- Single Responsibility Principle
- Separation of Concerns
- Builder Pattern
- Chain of Responsibility Pattern
- Strategy Pattern (Load Balancing)
- Modular Architecture

---

# Future Improvements

- Wildcard Route Matching (`/users/**`)
- Configuration-based Route Registration
- Service Discovery
- Circuit Breaker
- Retry Mechanism
- Health Checks
- JWT Authentication
- Docker Compose
- Metrics & Monitoring
- Distributed Tracing

---

# Running the Project

## Start User Service

```
cd user-service
mvn spring-boot:run
```

Runs on

```
localhost:8081
```

---

## Start Product Service

```
cd product-service
mvn spring-boot:run
```

Runs on

```
localhost:8082
```

---

## Start API Gateway

```
cd api-gateway
mvn spring-boot:run
```

Runs on

```
localhost:8080
```

---

# Example Requests

Get User

```
GET http://localhost:8080/users/32
```

Header

```
Authorization: valid-token
```

---

Get Product

```
GET http://localhost:8080/products/15
```

Header

```
Authorization: valid-token
```

---

# Learning Outcome

This project was built to understand how an API Gateway works internally instead of relying on existing frameworks.

It demonstrates request routing, middleware execution, load balancing, service registry, and HTTP forwarding while following clean architecture and object-oriented design principles.