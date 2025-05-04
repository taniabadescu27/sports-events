/**
 * This class was generated with the assistance of ChatGPT (AI).
 * The code was reviewed and validated to ensure it aligns with the project's functionality several adjustments were made:
	- Improved error handling and logging.
 */
package com.example.tracker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.example.tracker.client.ExternalApiClient;
import com.example.tracker.model.Event;


@Service
public class ScheduledEventPoller {

    private final EventStatusService eventStatusService;
    private final ExternalApiClient externalApiClient;
    private final EventPublishingService eventPublishingService;

    public ScheduledEventPoller(EventStatusService eventStateService, ExternalApiClient externalApiClient, EventPublishingService eventMessageProducer) {
        this.eventStatusService = eventStateService;
        this.externalApiClient = externalApiClient;
        this.eventPublishingService=eventMessageProducer;
    }
    private static final Logger log = LoggerFactory.getLogger(ScheduledEventPoller.class);

    @Scheduled(fixedRate = 10000)
    public void pollLiveEvents() {
        List<Event> liveEvents = eventStatusService.getLiveEvents();
        if (liveEvents.isEmpty()) return;
        for (Event event : liveEvents) {
            try {
                String response = externalApiClient.fetchDataForEvent(event.getEventId());
                eventPublishingService.publishEventToKafka(event, response);
                log.info("Published event update for ID: {}", response);
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                log.error("Network or HTTP error while polling event {}: {}", event.getEventId(), e.getMessage());
            } catch (IllegalArgumentException e) {
                log.error("Invalid data for event {}: {}", event.getEventId(), e.getMessage());
            } catch (NullPointerException e) {
                log.error("Null pointer exception for event {}: {}", event.getEventId(), e.getMessage());
            } catch (org.springframework.kafka.KafkaException e) {
                log.error("Kafka error while publishing event {}: {}", event.getEventId(), e.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error for event {}: {}", event.getEventId(), e.getMessage());
            }
        }
    }
}



