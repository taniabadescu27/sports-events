package com.example.tracker.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tracker.model.Event;
import com.example.tracker.model.Event.EventStatus;


@Service
public class EventStatusService {
	private final Map<String, Boolean> eventStatusMap = new ConcurrentHashMap<>();

    public void updateStatuses(List<Event> events) {
        for (Event event : events) {
            eventStatusMap.put(event.getEventId(), event.getStatus().equals(EventStatus.LIVE));
        }
    }

    // Generated with the help of AI (ChatGPT) to handle retry logic.
    public List<Event> getLiveEvents() {
        return eventStatusMap.entrySet().stream()
                .filter(entry -> entry.getValue())
                .map(entry -> new Event(entry.getKey(), EventStatus.LIVE))
                .collect(Collectors.toList());
    }
}
