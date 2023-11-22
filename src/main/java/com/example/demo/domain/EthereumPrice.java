package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "ethereumPrices")
public class EthereumPrice {
    @Id
    private String id;
    private Long timeStamp;
    private String price;

    public EthereumPrice() {
    }

    public EthereumPrice(String id, Long date, String price) {
        this.id = id;
        this.timeStamp = date;
        this.price = price;
    }

    public EthereumPrice(Long date, String price) {
        this.timeStamp = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
