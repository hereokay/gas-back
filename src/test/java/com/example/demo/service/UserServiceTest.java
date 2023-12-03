package com.example.demo.service;

import com.example.demo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void CorrectGasCostForGivenUserAddress() {
        String address = "0xf100c9ce60bd3e7a427bc1e5c8a3454c546211aa";
        Optional<User> userOptional = userService.getById(address);

        // Check if the user is present
        Assertions.assertThat(userOptional).isPresent();

        // Retrieve gasCost from user and compare
        BigDecimal spendGasUSDT = userOptional.get().getSpendGasUSDT();
        BigDecimal expectedSpendGasUSDT = new BigDecimal("11.81027585888").setScale(11, RoundingMode.HALF_UP);
        Assertions.assertThat(spendGasUSDT).isEqualTo(expectedSpendGasUSDT);
    }
}