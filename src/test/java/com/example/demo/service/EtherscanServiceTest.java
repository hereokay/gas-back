package com.example.demo.service;

import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EtherscanServiceTest {

    @Autowired
    private EtherscanService etherscanService;

    @Autowired
    private EthereumPriceService ethereumPriceService;

    @Test
    public void test(){
        String address = "0xB2F9BbC5db84a95B598cAB0C464cF92D584d8900";
        User user = etherscanService.fetchAndStoreUserFromExternalAPI(address);

        System.out.println(user.getGasCost());
    }

    @Test
    public void test2() throws Exception {
        ethereumPriceService.saveEthereumPricesFromFile("/Users/ijinjung/Downloads/demo 16/src/main/resources/eth_price.csv");
    }
}