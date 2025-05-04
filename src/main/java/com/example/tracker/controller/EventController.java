package com.example.tracker.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tracker.model.Event;
import com.example.tracker.service.EventStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventStatusService eventStateService;

    public EventController(EventStatusService eventStateService) {
        this.eventStateService = eventStateService;
    }

    @PostMapping("/status")
    public ResponseEntity<String> updateEventStatuses(@RequestBody @Valid List<Event> events) {
        eventStateService.updateStatuses(events);
        return ResponseEntity.ok("Statuses updated");
    }

}
