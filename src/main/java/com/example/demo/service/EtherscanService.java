package com.example.demo.service;

import com.example.demo.domain.GasCostSummary;
import org.json.JSONArray;
import org.json.JSONObject;
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
public class EtherscanService {

    private static final String ETHERSCAN_API_URL = "https://api.etherscan.io/api";

    @Value("${etherscan.api.key}")
    private String API_KEY ;
    private static final BigInteger WEI_IN_ETH = BigInteger.TEN.pow(18);

    public List<BigDecimal> getGasCostsInEth(String address) {
        ResponseEntity<String> response = getTransactionHistory(address);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray transactions = jsonResponse.getJSONArray("result");

        List<BigDecimal> gasCostsInEth = new ArrayList<>();

        for (int i = 0; i < transactions.length(); i++) {
            JSONObject transaction = transactions.getJSONObject(i);
            BigInteger gasPrice = new BigInteger(transaction.getString("gasPrice"));
            BigInteger gasUsed = new BigInteger(transaction.getString("gasUsed"));
            BigDecimal gasCostInWei = new BigDecimal(gasPrice.multiply(gasUsed));
            BigDecimal gasCost = gasCostInWei.divide(new BigDecimal(WEI_IN_ETH), 8, RoundingMode.HALF_UP);
            gasCostsInEth.add(gasCost);
        }

        return gasCostsInEth;
    }

    public ResponseEntity<String> getTransactionHistory(String address) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ETHERSCAN_API_URL)
                .queryParam("module", "account")
                .queryParam("action", "txlist")
                .queryParam("address", address)
                .queryParam("startblock", 0)
                .queryParam("endblock", 99999999)
                .queryParam("sort", "asc")
                .queryParam("apikey", API_KEY);

        return restTemplate.getForEntity(builder.toUriString(), String.class);
    }

    public GasCostSummary getGasCostSummary(String address) {
        List<BigDecimal> gasCostsInEth = getGasCostsInEth(address);
        BigDecimal totalCost = gasCostsInEth.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        int count = gasCostsInEth.size();
        return new GasCostSummary(totalCost, count);
    }

    public List<GasCostWithTimestamp> getGasCostsInEthWithTimestamp(String address) {
        ResponseEntity<String> response = getTransactionHistory(address);
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray transactions = jsonResponse.getJSONArray("result");

        List<GasCostWithTimestamp> gasCostsInEthWithTimestamp = new ArrayList<>();

        for (int i = 0; i < transactions.length(); i++) {
            JSONObject transaction = transactions.getJSONObject(i);
            BigInteger gasPrice = new BigInteger(transaction.getString("gasPrice"));
            BigInteger gasUsed = new BigInteger(transaction.getString("gasUsed"));
            BigDecimal gasCostInWei = new BigDecimal(gasPrice.multiply(gasUsed));
            BigDecimal gasCost = gasCostInWei.divide(new BigDecimal(WEI_IN_ETH), 8, RoundingMode.HALF_UP);

            // 트랜잭션의 timestamp 정보를 가져옵니다.
            String timestamp = transaction.getString("timeStamp");

            GasCostWithTimestamp entry = new GasCostWithTimestamp(gasCost, timestamp);
            gasCostsInEthWithTimestamp.add(entry);
        }

        return gasCostsInEthWithTimestamp;
    }


}

