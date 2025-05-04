package com.example.tracker.junit.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.tracker.model.Event;
import com.example.tracker.model.Event.EventStatus;
import com.example.tracker.service.EventStatusService;

public class EventStatusServiceTest {
	@Test
	 void testUpdateStatusAndGetLiveEvents() {
		List<Event> events = new ArrayList<>();
		events.add(new Event("event1", EventStatus.LIVE));
		events.add(new Event("event2", EventStatus.NOT_LIVE));
        EventStatusService service = new EventStatusService();

        service.updateStatuses(events);

        List<Event> liveEvents = service.getLiveEvents();

        assertEquals(1, liveEvents.size());
        assertTrue(liveEvents.contains(new Event("event1", EventStatus.LIVE)));
        assertFalse(liveEvents.contains(new Event("event2", EventStatus.NOT_LIVE)));
	 }

}
