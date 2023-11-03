package com.example.demo.service;

import com.example.demo.domain.GasCostSummary;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
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

    public User getUserWithTransactions(String address) {
        JSONObject jsonResponse = getJsonResponseFromEtherscan(address);
        JSONArray transactions = jsonResponse.getJSONArray("result");

        User user = new User();
        user.setAddress(address);

        List<Transaction> transactionList = buildTransactionList(transactions, user);

        BigDecimal totalGasCost = transactionList.stream()
                .map(Transaction::getGasCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        user.setGasCost(totalGasCost);
        user.setTransactions(transactionList);

        return user;
    }

    private JSONObject getJsonResponseFromEtherscan(String address) {
        ResponseEntity<String> response = getTransactionHistory(address);
        return new JSONObject(response.getBody());
    }

    private List<Transaction> buildTransactionList(JSONArray transactions, User user) {
        List<Transaction> transactionList = new ArrayList<>();

        for (int i = 0; i < transactions.length(); i++) {
            JSONObject transactionJson = transactions.getJSONObject(i);

            // 필요한 트랜잭션 데이터를 추출합니다.
            BigInteger gasPrice = new BigInteger(transactionJson.getString("gasPrice"));
            BigInteger gasUsed = new BigInteger(transactionJson.getString("gasUsed"));
            BigDecimal gasCostInWei = new BigDecimal(gasPrice.multiply(gasUsed));
            BigDecimal gasCost = gasCostInWei.divide(new BigDecimal(WEI_IN_ETH), 8, RoundingMode.HALF_UP);

            // 트랜잭션 객체를 생성하고 필드를 설정합니다.
            Transaction transaction = new Transaction();
            transaction.setGasCost(gasCost);
            transaction.setTimestamp(transactionJson.getString("timeStamp"));

            // 여기서 Transaction 객체의 address와 id를 설정합니다.
            transaction.setSender(user.getAddress()); // 사용자 주소로 설정
            transaction.setId(transactionJson.getString("hash")); // 고유한 트랜잭션 해시를 ID로 사용

            transactionList.add(transaction);
        }

        return transactionList;
    }


    private Transaction createTransactionFromJson(JSONObject transactionJson) {
        BigInteger gasPrice = new BigInteger(transactionJson.getString("gasPrice"));
        BigInteger gasUsed = new BigInteger(transactionJson.getString("gasUsed"));
        BigDecimal gasCostInWei = new BigDecimal(gasPrice.multiply(gasUsed));
        BigDecimal gasCost = gasCostInWei.divide(new BigDecimal(WEI_IN_ETH), 8, RoundingMode.HALF_UP);
        String timestamp = transactionJson.getString("timeStamp");

        Transaction transaction = new Transaction();
        transaction.setGasCost(gasCost);
        transaction.setTimestamp(timestamp);

        return transaction;
    }

}

