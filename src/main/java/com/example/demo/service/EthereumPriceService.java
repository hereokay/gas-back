package com.example.demo.service;

import com.example.demo.domain.EthereumPrice;
import com.example.demo.repository.EthereumPriceRepository;
import com.example.demo.utils.EthereumPriceParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EthereumPriceService {

    @Autowired
    private EthereumPriceRepository ethereumPriceRepository;

    public EthereumPrice savePrice(EthereumPrice price) {
        return ethereumPriceRepository.save(price);
    }

    public List<EthereumPrice> getAllPrices() {
        return ethereumPriceRepository.findAll();
    }

    public void saveEthereumPricesFromFile(String filePath) throws Exception {
        List<EthereumPrice> prices = EthereumPriceParser.parse(filePath);
        ethereumPriceRepository.saveAll(prices);
    }
}
