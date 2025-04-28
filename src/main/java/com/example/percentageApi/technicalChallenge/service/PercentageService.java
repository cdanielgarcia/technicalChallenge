package com.example.percentageApi.technicalChallenge.service;

import com.example.percentageApi.technicalChallenge.exception.ExternalServiceException;
import com.example.percentageApi.technicalChallenge.provider.PercentageProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PercentageService {

    private final AtomicReference<Double> cachedPercentage = new AtomicReference<>(null);
    private Instant lastUpdated = Instant.EPOCH;
    private final PercentageProvider percentageProvider;

    public PercentageService(PercentageProvider percentageProvider) {
        this.percentageProvider = percentageProvider;
    }

    public double getPercentage() {
        Instant now = Instant.now();
        long EXPIRATION_MINUTES = 30;
        if (cachedPercentage.get() != null && now.isBefore(lastUpdated.plusSeconds(EXPIRATION_MINUTES * 60))) {
            return cachedPercentage.get();
        }

        try {
            double percentage = percentageProvider.fetchPercentage();
            cachedPercentage.set(percentage);
            lastUpdated = now;
            return percentage;
        } catch (Exception e) {
            if (cachedPercentage.get() != null) {
                return cachedPercentage.get();
            }
            throw new ExternalServiceException("Failed to fetch percentage from external service and no cached value is available.");
        }
    }
}
