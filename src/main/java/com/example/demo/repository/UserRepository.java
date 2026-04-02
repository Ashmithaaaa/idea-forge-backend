package com.example.demo.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByName(String name);

    // ⭐ Leaderboard query
    List<User> findTop5ByOrderByReputationDesc();
    Optional<User> findByEmail(String email);

}