package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InfuraService extends AbstractBlockchainService {

    private static final String INFURA_API_URL = "https://mainnet.infura.io/v3/";

    @Value("${infura.api.key}")
    private String API_KEY;

    public ResponseEntity<String> getTransactionHistory(String address) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(INFURA_API_URL + API_KEY) // Infura uses the API key in the URL
                .queryParam("module", "proxy") // Example module, change as per Infura's documentation
                .queryParam("action", "eth_getTransactionByHash") // Example action, change as needed
                .queryParam("txhash", address); // Assuming 'address' is a transaction hash for this example

        return restTemplate.getForEntity(builder.toUriString(), String.class);
    }


}