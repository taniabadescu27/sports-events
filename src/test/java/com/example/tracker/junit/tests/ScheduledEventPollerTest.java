/**
 * This class was generated with the assistance of ChatGPT (AI).
 * The code was reviewed and validated to ensure it aligns with the project's functionality.
 */

package com.example.tracker.junit.tests;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.example.tracker.service.ScheduledEventPoller;

@SuppressWarnings("removal")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScheduledEventPollerTest {

    @SpyBean
    private ScheduledEventPoller scheduledEventPoller;

    @BeforeAll
    void waitToAllowScheduledExecutions() throws InterruptedException {
        Thread.sleep(22000); // wait for 2+ scheduled executions
    }

    @Test
    void testPollLiveEventsIsCalledPeriodically() {
        verify(scheduledEventPoller, atLeast(2)).pollLiveEvents();
    }
}
