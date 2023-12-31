package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findById(String address);
    List<User> findAllByOrderBySpendGasUSDTDesc();

}