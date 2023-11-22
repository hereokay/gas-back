package com.example.demo.service;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
import com.example.demo.utils.TransactionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtherscanService extends AbstractBlockchainService {

    private static final String ETHERSCAN_API_URL = "https://api.etherscan.io/api";

    @Value("${etherscan.api.key}")
    private String API_KEY;

    // 개별
    public ResponseEntity<String> getTransactionHistory(String address) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ETHERSCAN_API_URL)
                .queryParam("module", "account")
                .queryParam("action", "txlist")
                .queryParam("address", address)
                .queryParam("startblock", 0)
                .queryParam("endblock", 99999999)
                .queryParam("sort", "asc")
                .queryParam("apikey", API_KEY);

        return restTemplate.getForEntity(builder.toUriString(), String.class);
    }



}

