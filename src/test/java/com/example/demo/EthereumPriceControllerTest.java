package com.example.demo;

import com.example.demo.domain.EthereumPrice;
import com.example.demo.service.EthereumPriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EthereumPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EthereumPriceService ethereumPriceService;

    @Test
    public void testGetAllPrices() throws Exception {
        EthereumPrice price1 = new EthereumPrice("1", 1478131200000L, "10.81");
        EthereumPrice price2 = new EthereumPrice("2", 1478217600000L, "11.12");

        when(ethereumPriceService.getAllPrices()).thenReturn(Arrays.asList(price1, price2));

        mockMvc.perform(get("/ethereum/prices"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(1478131200000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("10.81"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").value(1478217600000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value("11.12"));
    }
}