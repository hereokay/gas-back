package com.example.demo.utils;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;

@Service
public class ParseUserFromCsv {

    @Autowired
    private UserRepository userRepository;

    public void readAndSave(String filePath){
        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                User user = new User();
                user.setSpendGasUSDT(new BigDecimal(record.get("total_calculated_value")));
                user.setAddress(record.get("address"));
                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
