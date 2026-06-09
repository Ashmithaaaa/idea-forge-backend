package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    // REGISTER USER
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    // LEADERBOARD API
    @GetMapping("/leaderboard")
    public List<User> leaderboard(){
        try {
            List<User> users = service.getTopInnovators();
            return users != null ? users : List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}