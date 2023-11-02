package com.example.demo.repository;

import com.example.demo.domain.EthereumPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EthereumPriceRepository extends MongoRepository<EthereumPrice, String> {
}
