package com.example.percentageApi.technicalChallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculationResponse {
    private double result;
    private double percentageUsed;
}
