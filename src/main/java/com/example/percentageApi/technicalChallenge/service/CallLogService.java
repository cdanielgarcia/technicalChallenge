package com.example.percentageApi.technicalChallenge.service;

import com.example.percentageApi.technicalChallenge.model.CallLog;
import com.example.percentageApi.technicalChallenge.repository.CallLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CallLogService {

    private final CallLogRepository callLogRepository;

    public CallLogService(CallLogRepository callLogRepository) {
        this.callLogRepository = callLogRepository;
    }

    public Page<CallLog> getHistory(Pageable pageable) {
        return callLogRepository.findAll(pageable);
    }

    @Async
    public void log(String endpoint, String params, String response, boolean success) {
        CallLog log = new CallLog();
        log.setEndpoint(endpoint);
        log.setParameters(params);
        log.setResponse(response);
        log.setSuccess(success);
        log.setTimestamp(LocalDateTime.now());
        callLogRepository.save(log);
    }
}
