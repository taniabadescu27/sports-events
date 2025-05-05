/**
 * This class was generated with the assistance of ChatGPT (AI).
 * The code was reviewed and validated to ensure it aligns with the project's functionality.
 */

package com.example.tracker;

import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.example.tracker.model.Event;
import com.example.tracker.model.Event.EventStatus;
import com.example.tracker.service.EventPublishingService;


@SuppressWarnings("removal")
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
public class KafkaPublisherTest {

    @Autowired
    private EventPublishingService eventMessageProducer;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;


    @Test
    void testKafkaSendRetriesOnFailure() throws Exception {
        Event testEvent = new Event("eventRetry", null);

        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
            .thenThrow(new KafkaException("Kafka unavailable"));

        eventMessageProducer.publishEventToKafka(testEvent, "{\"score\":\"0:0\"}");

        verify(kafkaTemplate, times(3)).send(anyString(), anyString(), anyString());
    }

    @Test
    void testKafkaSendSuccess() throws Exception {
        Event testEvent = new Event("eventSuccess", EventStatus.LIVE);

        SendResult<String, String> mockResult = mock(SendResult.class);
        CompletableFuture<SendResult<String, String>> successFuture = CompletableFuture.completedFuture(mockResult);

        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(successFuture);

        eventMessageProducer.publishEventToKafka(testEvent, "{\"score\":\"1:1\"}");

        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), anyString());
    }

}