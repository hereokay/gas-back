package com.example.demo.utils;

import com.example.demo.domain.EthereumPrice;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
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
            ethereumPrice.setTimeStamp(priceEntry.getLong(0));
            ethereumPrice.setPrice(Double.toString(priceEntry.getDouble(1))); // double 값을 String으로 변환하여 저장합니다.
            prices.add(ethereumPrice);
        }

        return prices;
    }

    public static List<EthereumPrice> parseWithCsv(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        List<EthereumPrice> prices = new ArrayList<>();

        // Skip the header line if present
        for (int i = 1; i < lines.size(); i++) {
            String[] priceEntry = lines.get(i).split(",");
            EthereumPrice ethereumPrice = new EthereumPrice();

            // Parse the date string into a LocalDate
            LocalDate date = LocalDate.parse(priceEntry[0]);
            // Convert LocalDate to Unix timestamp in milliseconds
            long timestamp = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

            ethereumPrice.setTimeStamp(timestamp); // Set the timestamp
            ethereumPrice.setPrice(priceEntry[1]); // Set the price
            prices.add(ethereumPrice);
        }

        return prices;
    }
}
