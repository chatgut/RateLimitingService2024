package com.iths.ratelimitingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RateLimitingService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean isAllowed(String userId, String userType) {
        String key = "rate_limit:" + userId;
        int maxRequests = getMaxRequests(userType);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        int requests = operations.get(key) != null ? Integer.parseInt(operations.get(key)) : 0;

        if (requests < maxRequests) {
            operations.increment(key, 1);
            if (requests == 0) {
                redisTemplate.expire(key, Duration.ofMinutes(1));
            }
            return true;
        }
        return false;
    }

    private int getMaxRequests(String userType) {
        return userType.equals("Premium") ? 10 : 5; // Premium users can make 10 requests per minute, basic users 5
    }
}
