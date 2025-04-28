package com.example.percentageApi.technicalChallenge.service;

import com.example.percentageApi.technicalChallenge.exception.ExternalServiceException;
import com.example.percentageApi.technicalChallenge.provider.PercentageProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PercentageServiceTest {

    @Test
    void testUseCachedPercentageWhenServiceFails() throws Exception {
        PercentageProvider mockProvider = mock(PercentageProvider.class);
        PercentageService percentageService = new PercentageService(mockProvider);

        Field cachedPercentageField = PercentageService.class.getDeclaredField("cachedPercentage");
        cachedPercentageField.setAccessible(true);
        AtomicReference<Double> cached = new AtomicReference<>(0.15);
        cachedPercentageField.set(percentageService, cached);

        Field lastUpdatedField = PercentageService.class.getDeclaredField("lastUpdated");
        lastUpdatedField.setAccessible(true);
        lastUpdatedField.set(percentageService, Instant.now());

        double percentage = percentageService.getPercentage();

        assertEquals(0.15, percentage, 0.0001);
    }

    @Test
    void testFetchNewPercentageWhenCacheIsEmpty() {
        PercentageProvider mockProvider = mock(PercentageProvider.class);
        when(mockProvider.fetchPercentage()).thenReturn(0.1);
        PercentageService percentageService = new PercentageService(mockProvider);

        double percentage = percentageService.getPercentage();

        assertEquals(0.1, percentage, 0.0001);
    }

    @Test
    void testThrowsExternalServiceExceptionWhenNoCachedValue() {
        PercentageProvider mockProvider = mock(PercentageProvider.class);
        when(mockProvider.fetchPercentage()).thenThrow(new RuntimeException("Service unavailable"));
        PercentageService percentageService = new PercentageService(mockProvider);

        ExternalServiceException ex = assertThrows(ExternalServiceException.class, percentageService::getPercentage);
        assertEquals("Failed to fetch percentage from external service and no cached value is available.", ex.getMessage());
    }
}
