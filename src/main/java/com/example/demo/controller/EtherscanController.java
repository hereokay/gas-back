package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.EtherscanService;
import com.example.demo.domain.GasCostSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/etherscan")
public class EtherscanController {

    @Autowired
    private EtherscanService etherscanService;

    @GetMapping("/transactions")
    public ResponseEntity<String> getTransactionHistory(@RequestParam String address) {
        return etherscanService.getTransactionHistory(address);
    }

    @GetMapping("/gas-costs-in-eth")
    public ResponseEntity<List<BigDecimal>> getGasCostsInEth(@RequestParam String address) {
        return ResponseEntity.ok(etherscanService.getGasCostsInEth(address));
    }

    @GetMapping("/gas-cost-summary")
    public ResponseEntity<GasCostSummary> getGasCostSummary(@RequestParam String address) {
        return ResponseEntity.ok(etherscanService.getGasCostSummary(address));
    }

    @GetMapping("/onlyGasUSDT")
    public BigDecimal getOnlyGasUSDT(@RequestParam String address) {
        User user = etherscanService.getUserWithTransactions(address);
        return user != null ? user.getSpendGasUSDT().setScale(2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
    }
}