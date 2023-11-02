package com.example.demo.domain;

import java.math.BigDecimal;

public class GasCostWithTimestamp {
    private BigDecimal gasCost;
    private String timestamp;

    public GasCostWithTimestamp(BigDecimal gasCost, String timestamp) {
        this.gasCost = gasCost;
        this.timestamp = timestamp;
    }

    public BigDecimal getGasCost() {
        return gasCost;
    }

    public void setGasCost(BigDecimal gasCost) {
        this.gasCost = gasCost;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
