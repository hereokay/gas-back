package com.example.demo.domain;

import java.math.BigDecimal;

public class GasCostSummary {
    private BigDecimal totalCostInEth;
    private int transactionCount;

    public GasCostSummary(BigDecimal totalCostInEth, int transactionCount) {
        this.totalCostInEth = totalCostInEth;
        this.transactionCount = transactionCount;
    }

    public BigDecimal getTotalCostInEth() {
        return totalCostInEth;
    }

    public void setTotalCostInEth(BigDecimal totalCostInEth) {
        this.totalCostInEth = totalCostInEth;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
}
