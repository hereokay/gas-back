package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private Long ranking;
    private BigDecimal gasCost;
    private BigDecimal spendGasUSDT;
    private String address;

    @DBRef
    private List<Transaction> transactions;

    public BigDecimal getSpendGasUSDT() {
        return spendGasUSDT;
    }

    public void setSpendGasUSDT(BigDecimal spendGasUSDT) {
        this.spendGasUSDT = spendGasUSDT;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public BigDecimal getGasCost() {
        return gasCost;
    }

    public void setGasCost(BigDecimal gasCost) {
        this.gasCost = gasCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}

