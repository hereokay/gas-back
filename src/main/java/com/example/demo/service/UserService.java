package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){

        // 이미 존재하는 경우
        if (userRepository.findByAddress(user.getAddress()).isPresent()){
            return null;
        }

        // 신규 유저
        return userRepository.save(user);
    }
    public Optional<User> getByAddress(String address){
        return userRepository.findByAddress(address);
    }

    public int calculateRanking(BigDecimal spendGasUSDT) {
        // 모든 사용자를 spendGasUSDT에 따라 정렬하고, 주어진 spendGasUSDT의 순위를 찾습니다.
        // 이 부분은 데이터베이스의 집계 쿼리로 구현될 수 있습니다.
        List<User> allUsers = userRepository.findAllByOrderBySpendGasUSDTDesc();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getSpendGasUSDT().compareTo(spendGasUSDT) <= 0) {
                return i + 1; // 순위는 1부터 시작
            }
        }
        return allUsers.size() + 1;
    }
}