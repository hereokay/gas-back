package com.example.demo.utils;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class ParseUserFromCsv {
    private static final Logger logger = LoggerFactory.getLogger(ParseUserFromCsv.class);


    @Autowired
    private UserRepository userRepository;

    public void readAndSave(String filePath) {
        //logger.info("Starting to process CSV file: {} on thread: {}", filePath, Thread.currentThread().getName());
        System.out.println("Starting to process CSV file: " + filePath);


        List<User> usersToInsert = new ArrayList<>();
        int BATCH_SIZE = 50; // You can adjust this size based on your requirements

        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                User user = new User();
                user.setSpendGasUSDT(new BigDecimal(record.get("total_calculated_value")));
                user.setAddress(record.get("address"));

                if (!userRepository.findByAddress(user.getAddress()).isPresent()) {
                    // Accumulate users for batch insert
                    usersToInsert.add(user);

                    // Check if batch size is reached, then insert and clear the list
                    if (usersToInsert.size() >= BATCH_SIZE) {
                        userRepository.saveAll(usersToInsert);
                        usersToInsert.clear();
                    }
                }
            }

            // Insert any remaining users
            if (!usersToInsert.isEmpty()) {
                userRepository.saveAll(usersToInsert);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Finished processing CSV file: " + filePath);
    }
}
