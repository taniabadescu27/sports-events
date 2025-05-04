/**
 * This class was generated with the assistance of ChatGPT (AI).
 * The code was reviewed and validated to ensure it aligns with the project's functionality several adjustments were made:
	- Improved error handling and logging.
 */

package com.example.tracker.service;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.example.tracker.model.Event;

import io.netty.handler.timeout.TimeoutException;



@Service
public class EventPublishingService {

    private static final Logger logger = LoggerFactory.getLogger(EventPublishingService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    private static final String TOPIC = "sports-events";

  

	public EventPublishingService(KafkaTemplate<String, String> kafkaTemplate, RetryTemplate retryTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.retryTemplate = retryTemplate;
	}



	public void publishEventToKafka(Event event, String messagePayload) {
    	 try {
    	        // Retry logic execution
    	        retryTemplate.execute(context -> {
    	            String message = "Event ID: " + event.getEventId() + " - " + messagePayload;
    	            logger.info("Trying to send message to Kafka. Attempt #: {}", context.getRetryCount() + 1);

    	            kafkaTemplate.send(TOPIC, event.getEventId(), messagePayload).get();

    	            logger.info("Successfully sent message on attempt #{} to Kafka for event {}: {}",context.getRetryCount() + 1,
    	            		event.getEventId(), message);
    	            return null;
    	        });
    	    } catch (InterruptedException e) {
    	        Thread.currentThread().interrupt();
    	        logger.error("Kafka message send interrupted for event {}: {}", event.getEventId(), e.getMessage(), e);
    	    } catch (ExecutionException e) {
    	        logger.error("Execution error while sending message to Kafka for event {}: {}", event.getEventId(), e.getMessage(), e);
    	    } catch (TimeoutException e) {
    	        logger.error("Timed out while sending message to Kafka for event {}: {}", event.getEventId(), e.getMessage(), e);
    	    } catch (MessagingException e) {
    	        logger.error("Messaging error occurred while sending message to Kafka for event {}: {}", event.getEventId(), e.getMessage(), e);
    	    } catch (Exception e) {
    	        logger.error("Failed to send message to Kafka for event {} after retries: {}", event.getEventId(), e.getMessage(), e);
    	    }
	}
}
