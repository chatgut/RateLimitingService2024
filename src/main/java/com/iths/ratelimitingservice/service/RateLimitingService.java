package com.iths.ratelimitingservice.service;
import org.springframework.stereotype.Service;

@Service
public class RateLimitingService {

    public boolean isAllowed(String userId) {
        // Pseudo-kod för att kontrollera och uppdatera räkneverk i Redis
        // Returnera true om användaren är under gränsen, annars false
        return true; // Tillfälligt, implementera logik här
    }
}