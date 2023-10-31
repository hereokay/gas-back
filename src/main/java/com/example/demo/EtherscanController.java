package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
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

}
