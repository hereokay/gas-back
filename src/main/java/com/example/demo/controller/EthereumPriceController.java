package com.example.demo.controller;

import com.example.demo.domain.EthereumPrice;
import com.example.demo.service.EthereumPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ethereum")
public class EthereumPriceController {

    @Autowired
    private EthereumPriceService ethereumPriceService;

    @PostMapping("/price")
    public EthereumPrice savePrice(@RequestBody EthereumPrice price) {
        return ethereumPriceService.savePrice(price);
    }

    @GetMapping("/prices")
    public List<EthereumPrice> getAllPrices() {
        return ethereumPriceService.getAllPrices();
    }
}
