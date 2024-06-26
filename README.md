﻿# RateLimitingService2024
RateLimitingService2024 is a microservice that provides rate limiting functionality for applications. It helps in controlling the number of requests a user can make in a given period.

## Prerequisites

- Java 22
- Maven 
- Docker
- Docker Compose

## Installation

1. **Clone the repository:**
    git clone https://github.com/your-organization/RateLimitingService2024.git
 
2. **Build the application:**
    mvn clean install

## Running the Application

### Using Docker Compose

1. **Build and run the containers:**
    docker-compose up --build

2. The application will be accessible at `http://localhost:8080`.

### Using Maven

1. **Run the application:**
    mvn spring-boot:run

## Endpoints
**POST /authenticate**: Generates a JWT token for authentication.
  - **Request Body:**
    ```json
    {
      "username": "user1"
    }
    ```
  - **Response:**
    ```json
    {
      "jwt": "your-jwt-token"
    }
    ```

- **GET /api/rate_limit_status**: Checks the rate limit status for a user.
  - **Query Parameters:**
    - `userId`: The ID of the user.
    - `userType`: The type of the user (e.g., Standard, Premium).
  - **Headers:**
    - `Authorization`: `Bearer your-jwt-token`

- **GET /api/request**: Processes a request if the rate limit is not exceeded.
  - **Query Parameters:**
    - `userId`: The ID of the user.
    - `userType`: The type of the user (e.g., Standard, Premium).
  - **Headers:**
    - `Authorization`: `Bearer your-jwt-token`

## Environment Variables

- `SPRING_DATA_REDIS_HOST`: The hostname for the Redis instance (default is `localhost`).
- `SPRING_DATA_REDIS_PORT`: The port for the Redis instance (default is `6379`).
- `JWT_SECRET`: The secret key for signing JWT tokens.

## CI/CD Pipeline

This project uses GitHub Actions for CI/CD. The workflow is defined in `.github/workflows/ci.yml`.

### Steps:
1. Checkout the repository.
2. Set up JDK 22.
3. Cache Maven packages.
4. Build the application with Maven.
5. Log in to Docker Hub.
6. Build and push the Docker image.

## FINISHED
