package com.example.tracker.client;

import org.springframework.stereotype.Service;

@Service
public class ExternalApiClient {

	public String fetchDataForEvent(String eventId) {
        return String.format("{\"eventId\": \"%s\", \"currentScore\": \"%d:%d\"}",
                eventId, (int)(Math.random() * 10), (int)(Math.random() * 10));
    }

}