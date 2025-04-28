package com.example.percentageApi.technicalChallenge.service;

import com.example.percentageApi.technicalChallenge.model.CalculationRequest;
import com.example.percentageApi.technicalChallenge.model.CalculationResponse;
import org.springframework.stereotype.Service;

@Service
public class
CalculationService {

    private final PercentageService percentageService;

    public CalculationService(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    public CalculationResponse calculate(CalculationRequest request) {
        double base = request.getNum1() + request.getNum2();
        double percentage = percentageService.getPercentage();
        double result = base * (1 + percentage);
        return new CalculationResponse(result, percentage);
    }
}
