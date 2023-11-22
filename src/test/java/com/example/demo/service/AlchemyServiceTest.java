package com.example.demo.service;

import com.example.demo.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlchemyServiceTest {

    @Autowired
    private AlchemyService alchemyService;

    @Test
    public void test(){
        String address = "0xB2F9BbC5db84a95B598cAB0C464cF92D584d8900";
        //User user = alchemyService.fetchAndStoreUserFromExternalAPI(address);

        //System.out.println(user.getGasCost());
    }
}