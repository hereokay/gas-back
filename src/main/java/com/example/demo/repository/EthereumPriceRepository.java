package com.example.demo.repository;

import com.example.demo.domain.EthereumPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EthereumPriceRepository extends MongoRepository<EthereumPrice, String> {
    EthereumPrice findFirstByTimeStampLessThanEqualOrderByTimeStampDesc(Long timeStamp);
}
