package com.example.demo.utils;

import com.example.demo.domain.EthereumPrice;
import org.json.JSONArray;
import org.json.JSONObject;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EthereumPriceParser {

    public static List<EthereumPrice> parse(String filePath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray pricesArray = jsonObject.getJSONArray("prices");

        List<EthereumPrice> prices = new ArrayList<>();

        for (int i = 0; i < pricesArray.length(); i++) {
            JSONArray priceEntry = pricesArray.getJSONArray(i);
            EthereumPrice ethereumPrice = new EthereumPrice();
            ethereumPrice.setDate(priceEntry.getLong(0));
            ethereumPrice.setPrice(priceEntry.getString(1));
            prices.add(ethereumPrice);
        }

        return prices;
    }
}
