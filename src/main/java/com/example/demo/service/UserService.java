package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // Register user
    public User register(User user){

        // ✅ 1. Null validation
        if(user.getEmail() == null || user.getPassword() == null){
            throw new RuntimeException("Email or Password cannot be null");
        }

        // ✅ 2. Duplicate check
        if(repo.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User already exists with this email");
        }

        try {
            return repo.save(user);
        } catch(Exception e){
            e.printStackTrace(); // 🔥 shows real error
            throw new RuntimeException("Signup failed");
        }
    }

    // ⭐ Leaderboard logic
    public List<User> getTopInnovators(){
        List<User> users = repo.findTop5ByOrderByReputationDesc();

        return users != null ? users : List.of(); // ✅ avoid null
    }
}