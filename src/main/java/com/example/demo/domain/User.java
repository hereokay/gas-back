package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;


@Document(collection = "users")
public class User {

    @Id
    private String id;
    private BigDecimal spendGasUSDT;

    private Long ranking;
    private BigDecimal gasCost;
    private String lastUpdate; //YYYY-MM-DD


    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public BigDecimal getSpendGasUSDT() {
        return spendGasUSDT;
    }

    public void setSpendGasUSDT(BigDecimal spendGasUSDT) {
        this.spendGasUSDT = spendGasUSDT;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

