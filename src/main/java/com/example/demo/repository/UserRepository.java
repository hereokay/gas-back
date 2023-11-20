package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByAddress(String address);
    List<User> findAllByOrderBySpendGasUSDTDesc();
}