package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpResponse;

@Service
public class AlchemyService extends AbstractBlockchainService {

    private static final String ALCHEMY_API_URL = "https://eth-mainnet.alchemyapi.io/v2/";

    @Value("${alchemy.api.key}")
    private String API_KEY;

    public ResponseEntity<String> getTransactionHistory(String address) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestJson = "{\"jsonrpc\":\"2.0\", \"id\": 1, \"method\": \"alchemy_getAssetTransfers\", \"params\": [{\"fromBlock\":\"0x0\", \"toBlock\": \"latest\", \"fromAddress\":\"" + address + "\", \"category\":[\"external\", \"internal\", \"erc20\", \"erc721\", \"erc1155\"]}]}";

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        String url = ALCHEMY_API_URL + API_KEY;

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            // Print the raw response for debugging
            System.out.println("Raw Response: " + response.getBody());

            // Now parse the response body
            JSONObject jsonObject = new JSONObject(response.getBody());
            if (jsonObject.has("result")) {
                JSONObject result = jsonObject.getJSONObject("result");
                // Add further processing if required
                return ResponseEntity.ok(result.toString());
            } else {
                return ResponseEntity.badRequest().body("No 'result' field in response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error occurred: " + e.getMessage());
        }
    }
}