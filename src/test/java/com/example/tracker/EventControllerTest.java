/**
 * This class was generated with the assistance of ChatGPT (AI).
 * The code was reviewed and validated to ensure it aligns with the project's functionality.
 */

package com.example.tracker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tracker.controller.EventController;
import com.example.tracker.model.Event;
import com.example.tracker.service.EventStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("removal")
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

	@MockBean
    private EventStatusService eventStateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUpdateEventStatus() throws Exception {
    	List<Event> events = new ArrayList<Event>();
    	events.add(new Event("event123", Event.EventStatus.LIVE));
 

        mockMvc.perform(post("/events/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(events)))
                .andExpect(status().isOk());
    }
}
