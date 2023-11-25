package com.example.demo.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParseUserFromCsvTest {

    @Autowired
    private ParseUserFromCsv parseUserFromCsv;

    @Test
    public void parse(){

        String path = "/Users/ijinjung/Downloads/google-cloud-sdk/gas_rank_result/";
        for (int i = 0; i < 400; i++) {
            String formattedNumber = String.format("%03d", i);
            String preFix = "jin-000000000";
            String filename = preFix + formattedNumber + ".csv";
            parseUserFromCsv.readAndSave(path + filename);
        }


    }

}