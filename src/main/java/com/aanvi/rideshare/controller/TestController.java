package com.aanvi.rideshare.controller;

import com.aanvi.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/test/db")
    public String testDatabase() {
        long count = userRepository.count();
        return "MongoDB Connected! Total users: " + count;
    }
}