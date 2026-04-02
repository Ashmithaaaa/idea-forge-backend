package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Idea;
import com.example.demo.service.VoteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin("*")
public class VoteController {

    @Autowired
    private VoteService voteService;

    // =========================
    // 👍 UPVOTE IDEA
    // =========================
    @PostMapping("/{ideaId}/upvote")
    public Idea upvote(@PathVariable Long ideaId,
                       HttpServletRequest request){

        String username = (String) request.getAttribute("username");

        if(username == null){
            throw new RuntimeException("Unauthorized");
        }

        return voteService.upvoteIdea(ideaId, username);
    }

    @PostMapping("/{ideaId}/downvote")
    public Idea downvote(@PathVariable Long ideaId,
                         HttpServletRequest request){

        String username = (String) request.getAttribute("username");

        if(username == null){
            throw new RuntimeException("Unauthorized");
        }

        return voteService.downvoteIdea(ideaId, username);
    }
}