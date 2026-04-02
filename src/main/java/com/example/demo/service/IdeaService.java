package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Idea;
import com.example.demo.model.User;
import com.example.demo.repository.IdeaRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.IdeaScoreCalculator;
import com.example.demo.util.IdeaSimilarityUtil;

@Service
public class IdeaService {

    @Autowired
    private IdeaRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ActivityService activityService;

    // ================================
    // GET ALL IDEAS
    // ================================

    public List<Idea> getAllIdeas() {
        return repo.findAll();
    }

    // ================================
    // CREATE IDEA
    // ================================

    public Idea createIdea(Idea idea) {

        if (idea.getCategory() != null) {
            idea.setCategory(idea.getCategory().trim().toUpperCase());
        }

        List<Idea> similarIdeas = new ArrayList<>();

        for (Idea existing : repo.findAll()) {

            if (idea.getProblemStatement() == null || existing.getProblemStatement() == null)
                continue;

            double similarity =
                    IdeaSimilarityUtil.calculateSimilarity(
                            idea.getProblemStatement(),
                            existing.getProblemStatement()
                    );

            if (similarity > 0.6) {
                similarIdeas.add(existing);
            }
        }

        if (!similarIdeas.isEmpty()) {
            System.out.println("⚠ Similar ideas detected:");
            for (Idea i : similarIdeas) {
                System.out.println(i.getTitle());
            }
        }

        Idea savedIdea = repo.save(idea);

        // Log activity
        activityService.logActivity(
                idea.getAuthorName(),
                "submitted idea",
                idea.getTitle()
        );

        // Increase user reputation
        for (User user : userRepo.findAll()) {

            if (user.getName() != null &&
                user.getName().equalsIgnoreCase(idea.getAuthorName())) {

                user.setReputation(user.getReputation() + 5);
                userRepo.save(user);
            }
        }

        return savedIdea;
    }

    // ================================
    // GET IDEA BY ID
    // ================================

    public Idea getIdea(Long id) {

        Idea idea = repo.findById(id).orElse(null);

        if (idea != null) {
            idea.setViews(idea.getViews() + 1);
            repo.save(idea);
        }

        return idea;
    }

    // ================================
    // DELETE IDEA
    // ================================

    public void deleteIdea(Long id) {
        repo.deleteById(id);
    }

    // ================================
    // TRENDING IDEAS
    // ================================

    public List<Idea> getTrendingIdeas() {

        List<Idea> ideas = repo.findAll();

        ideas.sort(
                Comparator.comparingDouble(
                        idea -> -IdeaScoreCalculator.calculateScore(idea)
                )
        );

        return ideas;
    }

    // ================================
    // SEARCH IDEAS
    // ================================

    public List<Idea> searchIdeas(String keyword) {
        return repo.findByTitleContainingIgnoreCase(keyword);
    }

    // ================================
    // FILTER IDEAS
    // ================================

    public List<Idea> filterIdeas(String category) {
        return repo.findByCategoryIgnoreCase(category);
    }

    // ================================
    // FIND SIMILAR IDEAS
    // ================================

    public List<Idea> findSimilarIdeas(Idea idea) {

        List<Idea> result = new ArrayList<>();

        for (Idea existing : repo.findAll()) {

            if (existing.getId() != null &&
                existing.getId().equals(idea.getId())) {
                continue;
            }

            if (idea.getProblemStatement() == null || existing.getProblemStatement() == null)
                continue;

            double similarity =
                    IdeaSimilarityUtil.calculateSimilarity(
                            idea.getProblemStatement(),
                            existing.getProblemStatement()
                    );

            if (similarity > 0.5) {
                result.add(existing);
            }
        }

        return result;
    }

    // ================================
    // TEAM MATCHING (FIXED 🚀)
    // ================================

    public List<User> findMatchingUsers(Long ideaId) {

        Idea idea = repo.findById(ideaId).orElse(null);

        if (idea == null) {
            return new ArrayList<>();
        }

        // ✅ FIX 1: Null-safe tech stack
        if (idea.getTechnologyStack() == null ||
            idea.getTechnologyStack().isEmpty()) {
            return new ArrayList<>();
        }

        String techStack = idea.getTechnologyStack().toLowerCase();
        String[] techs = techStack.split(",");

        List<User> matchedUsers = new ArrayList<>();

        for (User user : userRepo.findAll()) {

            // ✅ FIX 2: Null-safe skills
            if (user.getSkills() == null || user.getSkills().isEmpty())
                continue;

            String skills = user.getSkills().toLowerCase();

            for (String tech : techs) {

                if (skills.contains(tech.trim())) {
                    matchedUsers.add(user);
                    break;
                }
            }
        }

        return matchedUsers;
    }
}