package com.example.demo;

import com.example.demo.service.EtherscanService;
import com.example.demo.etherscan.GasCostSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EtherscanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtherscanService etherscanService;

    @Test
    public void testGetGasCostSummary() throws Exception {
        String address = "0xAEf30fEcf1792Eed810F5fab03c0eB584E83FB91";
        GasCostSummary mockSummary = new GasCostSummary(new BigDecimal("0.02904956"), 20);

        when(etherscanService.getGasCostSummary(address)).thenReturn(mockSummary);

        mockMvc.perform(get("/api/etherscan/gas-cost-summary")
                        .param("address", address)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"totalCostInEth\":0.02904956,\"transactionCount\":20}"));
    }

    @Test
    public void testGetGasCostsInEth() throws Exception {
        String address = "0xe63a7377D863861F0bCC16dF9cb7C8F9A9Ab6d47";

        // Expected result
        List<BigDecimal> expectedGasCosts = Arrays.asList(
                new BigDecimal("0.00076466"),
                new BigDecimal("0.00073135")
        );

        // Mock the service call
        when(etherscanService.getGasCostsInEth(address)).thenReturn(expectedGasCosts);

        mockMvc.perform(get("/api/etherscan/gas-costs-in-eth")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedGasCosts)));
    }
}
