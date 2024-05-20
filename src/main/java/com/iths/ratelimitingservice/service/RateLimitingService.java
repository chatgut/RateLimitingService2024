package com.iths.ratelimitingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitingService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RateLimitingService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String userId, String userType) {
        String key = "rate_limit:" + userId;
        int maxRequests = getMaxRequests(userType);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Integer requests = operations.get(key) != null ? Integer.parseInt(operations.get(key)) : 0;

        if (requests < maxRequests) {
            operations.increment(key);
            if (requests == 0) {
                redisTemplate.expire(key, Duration.ofMinutes(1)); // Reset the counter every minute
            }
            System.out.println("Requests: " + (requests + 1));
            return true;
        }
        System.out.println("Rate limit exceeded for user: " + userId);
        return false;
    }

    public int getRemainingRequests(String userId, String userType) {
        String key = "rate_limit:" + userId;
        int maxRequests = getMaxRequests(userType);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int requests = operations.get(key) != null ? Integer.parseInt(operations.get(key)) : 0;

        return Math.max(0, maxRequests - requests); // Ensure non-negative remaining requests
    }

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
