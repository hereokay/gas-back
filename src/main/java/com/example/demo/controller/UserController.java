package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.EtherscanService;
import com.example.demo.service.UserService;
import com.example.demo.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private EtherscanService etherscanService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Optional<User> getUserWithTransactions(@RequestParam String address) {
        String lowerAddress = AddressUtils.convertHexToLowerCase(address);
        return userService.getByAddress(lowerAddress);
    }
}
