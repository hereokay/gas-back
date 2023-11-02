package com.example.demo;

import com.example.demo.service.EthereumPriceService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
public class EthereumPriceServiceTest {

    @Autowired
    private EthereumPriceService ethereumPriceService;

    @Test
    public void testSaveEthereumPricesFromFile() {
        try {
            // ClassPathResource를 사용하여 resources 폴더 내의 파일을 로드
            String filePath = new ClassPathResource("prices.json").getFile().getAbsolutePath();

            // EthereumPriceService의 saveEthereumPricesFromFile 메서드 호출
            ethereumPriceService.saveEthereumPricesFromFile(filePath);

            System.out.println("Prices have been successfully saved!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving prices from file.");
        }
    }

    @Test
    public void testPriceEntryContent() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(new ClassPathResource("prices.json").getFile().getAbsolutePath())));
        JSONObject jsonObject = new JSONObject(content);
        JSONArray pricesArray = jsonObject.getJSONArray("prices");

        for (int i = 0; i < pricesArray.length(); i++) {
            JSONArray priceEntry = pricesArray.getJSONArray(i);
            System.out.println("Entry " + i + ": " + priceEntry.toString());
        }
    }

}
