package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.AlchemyService;
import com.example.demo.service.EtherscanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private EtherscanService etherscanService;

    @Autowired
    private AlchemyService alchemyService;

    @GetMapping("/user")
    public ResponseEntity<User> getUserWithTransactions(@RequestParam String address) {
        User user = alchemyService.fetchAndStoreUserFromExternalAPI(address);
        return ResponseEntity.ok(user);
    }
}
