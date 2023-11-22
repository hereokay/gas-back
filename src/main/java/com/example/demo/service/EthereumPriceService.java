package com.example.demo.service;

import com.example.demo.domain.EthereumPrice;
import com.example.demo.repository.EthereumPriceRepository;
import com.example.demo.utils.EthereumPriceParser;
import com.example.demo.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        List<EthereumPrice> prices = EthereumPriceParser.parseWithCsv(filePath);
        ethereumPriceRepository.saveAll(prices);
    }

    public BigDecimal getPriceAtTime(Long timeStamp) {
        EthereumPrice priceRecord = ethereumPriceRepository.findFirstByTimeStampLessThanEqualOrderByTimeStampDesc(timeStamp);
        if (priceRecord != null) {
            return new BigDecimal(priceRecord.getPrice());
        } else {
            // 적절한 예외 처리 또는 로그 메시지를 추가하세요.
            return new BigDecimal(0); // 또는 예외를 던지거나, 적절한 기본값 반환
        }
    }
}