package com.example.demo.service;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
import com.example.demo.utils.TransactionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlockchainService {

    @Autowired
    protected RestTemplate restTemplate;

    private static final BigInteger WEI_IN_ETH = BigInteger.TEN.pow(18);

    @Autowired
    private EthereumPriceService ethereumPriceService;

    @Autowired
    private UserService userService;

    public User fetchAndStoreUserFromExternalAPI(String address) {
        User user = new User(address);
        List<Transaction> transactionList = buildTransactionList(getTransactionsFromExternalAPI(address), user);
        calculateTransactionStatistics(user, transactionList);

//         순위 계산 후 할당
        int ranking = userService.calculateRanking(user.getSpendGasUSDT());
        user.setRanking((long) ranking);

        userService.saveUser(user);
        return user;
    }

    protected JSONArray getTransactionsFromExternalAPI(String address) {
        JSONObject jsonResponse = getJsonResponseFromExternalAPI(address);
        return jsonResponse.getJSONArray("result");
    }

    protected void calculateTransactionStatistics(User user, List<Transaction> transactionList) {
        BigDecimal totalSpendGasUSDT = calculateTotalSpendGasUSDT(transactionList);
        user.setSpendGasUSDT(totalSpendGasUSDT);
        user.setGasCost(calculateTotalGasCost(transactionList));
        user.setTransactions(transactionList);
    }

    protected BigDecimal calculateTotalSpendGasUSDT(List<Transaction> transactionList) {
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction transaction : transactionList) {
            BigDecimal ethPriceAtMidnight = ethereumPriceService.getPriceAtTime(
                    TransactionUtils.toMidnightTimeStamp(transaction.getTimeStamp()));
            total = total.add(transaction.getGasCost().multiply(ethPriceAtMidnight));
        }
        return total;
    }

    protected BigDecimal calculateTotalGasCost(List<Transaction> transactionList) {
        return transactionList.stream()
                .map(Transaction::getGasCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    protected abstract ResponseEntity<String> getTransactionHistory(String address) ;

    protected JSONObject getJsonResponseFromExternalAPI(String address) {
        ResponseEntity<String> response = getTransactionHistory(address);
        return new JSONObject(response.getBody());
    }

    protected List<Transaction> buildTransactionList(JSONArray transactions, User user) {
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

            // 기존의 타임스탬프 값을 가져와 1000을 곱한다.
            Long timeStampInSeconds = transactionJson.getLong("timeStamp");
            Long timeStampInMilliseconds = timeStampInSeconds * 1000;
            transaction.setTimeStamp(timeStampInMilliseconds);

            // 여기서 Transaction 객체의 address와 id를 설정합니다.
            transaction.setSender(user.getAddress()); // 사용자 주소로 설정
            transaction.setId(transactionJson.getString("hash")); // 고유한 트랜잭션 해시를 ID로 사용

            transactionList.add(transaction);
        }

        return transactionList;
    }
}
