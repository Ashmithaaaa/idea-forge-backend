package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Idea;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.IdeaRepository;
import com.example.demo.repository.VoteRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private VoteRepository voteRepo;

    // Overall stats
    @GetMapping("/stats")
    public Map<String,Object> getStats(){

        Map<String,Object> stats = new HashMap<>();

        stats.put("totalIdeas", ideaRepo.count());
        stats.put("totalComments", commentRepo.count());
        stats.put("totalVotes", voteRepo.count());

        return stats;
    }

    @GetMapping("/categories")
    public Map<String,Integer> ideasByCategory(){

        Map<String,Integer> result = new HashMap<>();

        List<Idea> ideas = ideaRepo.findAll();

        for(Idea idea : ideas){

            String category = idea.getCategory();

            // handle null category
            if(category == null || category.trim().isEmpty()){
                category = "Other";
            }

            result.put(
                category,
                result.getOrDefault(category,0) + 1
            );
        }

        return result;
    }

}