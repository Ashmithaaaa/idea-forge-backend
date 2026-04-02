package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Idea;
import com.example.demo.model.User;
import com.example.demo.repository.IdeaRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IdeaService;

@RestController
@RequestMapping("/api/ideas")
@CrossOrigin(origins = "http://localhost:5174") // ✅ FIXED PORT
public class IdeaController {

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IdeaService ideaService;

    // ===============================
    // GET ALL IDEAS (PUBLIC)
    // ===============================
    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaService.getAllIdeas();
    }

    // ===============================
    // GET IDEA BY ID (PUBLIC)
    // ===============================
    @GetMapping("/{id}")
    public Idea getIdeaById(@PathVariable Long id) {
        return ideaService.getIdea(id);
    }

    // ===============================
    // CREATE IDEA (FIXED ✅)
    // ===============================
    @PostMapping
    public Idea createIdea(@RequestBody Idea idea,
                           HttpServletRequest request) {

        // 🔍 Try to get username from JWT (if exists)
        String username = (String) request.getAttribute("username");

        // ✅ Fallback: take from frontend
        if (username == null) {
            username = idea.getAuthorName();
        }

        // ✅ Final fallback
        if (username == null || username.isEmpty()) {
            username = "Anonymous";
        }

        idea.setAuthorName(username);

        // 🔍 DEBUG (optional - remove later)
        System.out.println("TITLE: " + idea.getTitle());
        System.out.println("AUTHOR: " + idea.getAuthorName());
        System.out.println("CATEGORY: " + idea.getCategory());

        return ideaService.createIdea(idea);
    }

    // ===============================
    // TRENDING IDEAS
    // ===============================
    @GetMapping("/trending")
    public List<Idea> getTrendingIdeas() {
        return ideaService.getTrendingIdeas();
    }

    // ===============================
    // SEARCH IDEAS
    // ===============================
    @GetMapping("/search")
    public List<Idea> searchIdeas(@RequestParam String keyword) {
        return ideaService.searchIdeas(keyword);
    }

    // ===============================
    // FILTER IDEAS
    // ===============================
    @GetMapping("/category")
    public List<Idea> filterIdeas(@RequestParam String category) {
        return ideaService.filterIdeas(category);
    }

    // ===============================
    // CHECK SIMILAR IDEAS
    // ===============================
    @GetMapping("/check-similar")
    public List<Idea> checkSimilar(@RequestParam String text) {

        if (text == null || text.isEmpty()) {
            return List.of();
        }

        return ideaRepo.findAll()
                .stream()
                .filter(i ->
                        i.getProblemStatement() != null &&
                        i.getProblemStatement().toLowerCase()
                                .contains(text.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ===============================
    // GET SIMILAR IDEAS
    // ===============================
    @GetMapping("/similar/{ideaId}")
    public List<Idea> getSimilarIdeas(@PathVariable Long ideaId) {

        Idea idea = ideaRepo.findById(ideaId).orElse(null);

        if (idea == null || idea.getCategory() == null) {
            return List.of();
        }

        String category = idea.getCategory();

        return ideaRepo.findAll()
                .stream()
                .filter(i ->
                        !i.getId().equals(ideaId) &&
                        i.getCategory() != null &&
                        i.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // ===============================
    // MATCH USERS FOR IDEA
    // ===============================
    @GetMapping("/match/{ideaId}")
    public List<User> getMatchingUsers(@PathVariable Long ideaId) {
        return ideaService.findMatchingUsers(ideaId);
    }
}