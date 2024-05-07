package com.iths.ratelimitingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.iths.ratelimitingservice.service.RateLimitingService;

@RestController
public class RequestController {

    @Autowired
    private RateLimitingService rateLimitingService;

    @GetMapping("/api/request")
    public String handleRequest(@RequestParam String userId, @RequestParam String userType) {
        if (rateLimitingService.isAllowed(userId, userType)) {
            return "Request processed successfully";
        } else {
            return "Too many requests";
        }
    }
}
