package com.iths.ratelimitingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.iths.ratelimitingservice.service.RateLimitingService;

@RestController
public class RateLimitStatusController {

    @Autowired
    private RateLimitingService rateLimitingService;

    @GetMapping("/api/rate_limit_status")
    public ResponseEntity<?> getRateLimitStatus(@RequestParam String userId, @RequestParam String userType) {
        int remainingRequests = rateLimitingService.getRemainingRequests(userId, userType);
        if (remainingRequests >= 0) {
            return ResponseEntity.ok().body("You have " + remainingRequests + " requests remaining this minute.");
        } else {
            return ResponseEntity.badRequest().body("Could not retrieve rate limit status.");
        }
    }
}
