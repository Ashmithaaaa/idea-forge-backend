package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Idea;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long>{

    // =========================
    // Search ideas by title
    // =========================
    List<Idea> findByTitleContainingIgnoreCase(String keyword);


    // =========================
    // Filter ideas by category
    // =========================
    List<Idea> findByCategoryIgnoreCase(String category);
    List<Idea> findTop5ByOrderByVotesDesc();
}