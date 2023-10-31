package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EtherscanServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EtherscanService etherscanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGasCostSummary() {

        GasCostSummary gasCostSummary = etherscanService.getGasCostSummary("0xAEf30fEcf1792Eed810F5fab03c0eB584E83FB91");

        // 결과 출력
        System.out.println("Total Gas Cost in ETH: " + gasCostSummary.getTotalCostInEth());
        System.out.println("Number of Transactions: " + gasCostSummary.getTransactionCount());
    }
}