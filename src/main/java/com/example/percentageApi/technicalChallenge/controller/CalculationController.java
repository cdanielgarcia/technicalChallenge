package com.example.percentageApi.technicalChallenge.controller;

import com.example.percentageApi.technicalChallenge.model.CalculationRequest;
import com.example.percentageApi.technicalChallenge.model.CalculationResponse;
import com.example.percentageApi.technicalChallenge.model.CallLog;
import com.example.percentageApi.technicalChallenge.service.CalculationService;
import com.example.percentageApi.technicalChallenge.service.CallLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculationController {

    private final CalculationService calculationService;
    private final CallLogService callLogService;


    public CalculationController(CalculationService calculationService, CallLogService callLogService) {
        this.calculationService = calculationService;
        this.callLogService = callLogService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        try {
            CalculationResponse result = calculationService.calculate(request);
            callLogService.log("/calculate", request.toString(), result.toString(), true);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            callLogService.log("/calculate", request.toString(), ex.getMessage(), false);
            throw ex;
        }
    }

    @GetMapping("/history")
    public Page<CallLog> getHistory(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return callLogService.getHistory(PageRequest.of(page, size));
    }
}
