package com.example.percentageApi.technicalChallenge.service;


import com.example.percentageApi.technicalChallenge.model.CalculationRequest;
import com.example.percentageApi.technicalChallenge.model.CalculationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculationServiceTest {

    @Test
    void testCalculate() {
        PercentageService mockPercentageService = mock(PercentageService.class);
        when(mockPercentageService.getPercentage()).thenReturn(0.1);

        CalculationService service = new CalculationService(mockPercentageService);
        CalculationRequest req = new CalculationRequest();
        req.setNum1(100);
        req.setNum2(100);

        CalculationResponse res = service.calculate(req);

        assertEquals(220.0, res.getResult(), 0.0001);
        assertEquals(0.1, res.getPercentageUsed(), 0.0001);
    }
}