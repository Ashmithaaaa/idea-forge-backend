package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Idea;
import com.example.demo.model.Notification;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.IdeaRepository;
import com.example.demo.repository.NotificationRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repo;

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private ActivityService activityService;

    // =========================
    // ADD COMMENT
    // =========================
    public Comment addComment(Comment comment, String username){

        comment.setUsername(username);

        Comment saved = repo.save(comment);

        // log activity
        activityService.logActivity(
            username,
            "commented on",
            "Idea " + comment.getIdeaId()
        );

        // notify idea owner
        Idea idea = ideaRepo.findById(comment.getIdeaId()).orElse(null);

        if(idea != null){

            Notification n = new Notification();
            n.setUsername(idea.getAuthorName());
            n.setMessage(username + " commented on your idea: " + idea.getTitle());

            notificationRepo.save(n);
        }

        return saved;
    }

    // =========================
    // GET COMMENTS
    // =========================
    public List<Comment> getComments(Long ideaId){
        return repo.findByIdeaId(ideaId);
    }
}