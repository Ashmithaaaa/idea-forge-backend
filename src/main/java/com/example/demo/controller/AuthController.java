package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // ✅ FIXED
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public User signup(@RequestBody User user){

        if(user.getEmail() == null || user.getPassword() == null){
            throw new RuntimeException("Email and Password required");
        }

        return authService.signup(user);
    }

    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody Map<String,String> request){

        String email = request.get("email");
        String password = request.get("password");

        if(email == null || password == null){
            throw new RuntimeException("Invalid login data");
        }

        User user = authService.login(email, password);

        String token = jwtUtil.generateToken(user.getName());

        return new AuthResponse(token, user);
    }
}