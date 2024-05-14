package com.iths.ratelimitingservice.controller;

import com.iths.ratelimitingservice.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthController {

    private final JwtUtil jwtUtil;

    // Constructor injection of the JwtUtil
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        try {
            // Dummy validation for username
            if ("user1".equals(authenticationRequest.getUsername())) {
                final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
                return ResponseEntity.ok(Collections.singletonMap("jwt", jwt));
            } else {
                throw new Exception("Invalid username");
            }
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Authentication failed");
        }
    }
}

class AuthRequest {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}