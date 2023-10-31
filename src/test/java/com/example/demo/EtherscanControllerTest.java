package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/etherscan/gas-cost-summary")
                        .param("address", address)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"totalCostInEth\":0.02904956,\"transactionCount\":20}"));
    }
}
