package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.AddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Optional<User> getUser(@RequestParam String address) {
        String lowerAddress = AddressUtils.convertHexToLowerCase(address);
        return userService.getByAddress(lowerAddress);
    }
    @PostMapping("/user/update")
    public ResponseEntity<Optional<User>> updateUser(@RequestBody String address) {
        // Assuming the address is the unique identifier for the user.
        Optional<User> updatedUser = userService.updateUserAddress(address);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }


}
