package com.iths.ratelimitingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

class RateLimitingServiceTest {

    private RateLimitingService service;
    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations valueOperations;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        service = new RateLimitingService(redisTemplate);
    }

    @Test
    void isAllowed_ReturnsTrue_WhenUnderLimit() {
        when(valueOperations.get("rate_limit:userId")).thenReturn("1");
        boolean result = service.isAllowed("userId", "Standard");
        assertEquals(true, result);
    }

    @Test
    void isAllowed_ReturnsFalse_WhenOverLimit() {
        when(valueOperations.get("rate_limit:userId")).thenReturn("10");
        boolean result = service.isAllowed("userId", "Standard");
        assertEquals(false, result);
    }
}
