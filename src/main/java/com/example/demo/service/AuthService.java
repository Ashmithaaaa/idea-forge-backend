package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    // ====================
    // SIGNUP
    // ====================
    public User signup(User user){

        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new RuntimeException("Email already registered");
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getReputation() == 0){
            user.setReputation(0);
        }

        return userRepo.save(user);
    }


    // ====================
    // LOGIN
    // ====================
    public User login(String email,String password){

        Optional<User> userOptional = userRepo.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        // Compare encrypted password
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}