package com.example.percentageApi.technicalChallenge.service;

import com.example.percentageApi.technicalChallenge.model.CallLog;
import com.example.percentageApi.technicalChallenge.repository.CallLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CallLogServiceTest {

    private CallLogRepository callLogRepository;
    private CallLogService callLogService;

    @BeforeEach
    void setUp() {
        callLogRepository = mock(CallLogRepository.class);
        callLogService = new CallLogService(callLogRepository);
    }

    @Test
    void testLog() {
        String endpoint = "/test";
        String params = "param1=value1";
        String response = "OK";
        boolean success = true;

        callLogService.log(endpoint, params, response, success);

        ArgumentCaptor<CallLog> captor = ArgumentCaptor.forClass(CallLog.class);
        verify(callLogRepository, times(1)).save(captor.capture());

        CallLog savedLog = captor.getValue();
        assertEquals(endpoint, savedLog.getEndpoint());
        assertEquals(params, savedLog.getParameters());
        assertEquals(response, savedLog.getResponse());
        assertEquals(success, savedLog.isSuccess());
        assertNotNull(savedLog.getTimestamp());
    }
}