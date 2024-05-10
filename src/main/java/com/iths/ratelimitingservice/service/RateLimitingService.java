package com.iths.ratelimitingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Objects;

@Service
public class RateLimitingService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RateLimitingService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Checks if the user is allowed to make a request based on their rate limit.
     *
     * @param userId   the ID of the user making the request
     * @param userType the type of the user (e.g., "Standard", "Premium")
     * @return true if the user is allowed to make a request, false otherwise
     */
    public boolean isAllowed(String userId, String userType) {
        String key = "rate_limit:" + userId;
        int maxRequests = getMaxRequests(userType);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int requests = operations.get(key) != null ? Integer.parseInt(Objects.requireNonNull(operations.get(key))) : 0;

        if (requests < maxRequests) {
            operations.increment(key);
            if (requests == 0) {
                redisTemplate.expire(key, Duration.ofMinutes(1)); // Reset the counter every minute
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves the number of remaining requests a user can make within the current rate limit window.
     *
     * @param userId   the ID of the user
     * @param userType the type of the user (e.g., "Standard", "Premium")
     * @return the number of remaining requests the user can make
     */
    public int getRemainingRequests(String userId, String userType) {
        String key = "rate_limit:" + userId;
        int maxRequests = getMaxRequests(userType);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Integer requests = operations.get(key) != null ? Integer.parseInt(Objects.requireNonNull(operations.get(key))) : 0;

        return maxRequests - requests;
    }

    /**
     * Determines the maximum number of requests a user can make in a minute based on their user type.
     *
     * @param userType the type of the user
     * @return the maximum number of requests allowed
     */
    private int getMaxRequests(String userType) {
        switch (userType) {
            case "Premium":
                return 15;  // Premium users can make 15 requests per minute
            case "Standard":
                return 10;  // Standard users can make 10 requests per minute
            default:
                return 5;   // Default users can make 5 requests per minute
        }
    }
}
