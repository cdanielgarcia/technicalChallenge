package com.example.percentageApi.technicalChallenge.provider;

import org.springframework.stereotype.Component;

@Component
public class StaticPercentageProvider implements PercentageProvider {
    @Override
    public double fetchPercentage() {
        return 0.1;
    }
}

