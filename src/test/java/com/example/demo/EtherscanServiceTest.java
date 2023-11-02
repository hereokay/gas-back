package com.example.demo;

import com.example.demo.service.EtherscanService;
import com.example.demo.etherscan.GasCostSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.eq;

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