package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin("*")
public class CommentController {

    @Autowired
    private CommentService service;

    // =========================
    // ADD COMMENT (PROTECTED)
    // =========================
    @PostMapping
    public Comment addComment(@RequestBody Comment comment,
                              HttpServletRequest request){

        String username = (String) request.getAttribute("username");

        if(username == null){
            throw new RuntimeException("Unauthorized");
        }

        return service.addComment(comment, username);
    }

    // =========================
    // GET COMMENTS BY IDEA
    // =========================
    @GetMapping("/{ideaId}")
    public List<Comment> getComments(@PathVariable Long ideaId){

        return service.getComments(ideaId);
    }
}