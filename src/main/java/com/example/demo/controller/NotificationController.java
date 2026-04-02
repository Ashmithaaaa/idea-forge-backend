package com.example.demo.controller;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    // Get notifications for user
    @GetMapping("/{username}")
    public List<Notification> getNotifications(@PathVariable String username){

        return repo.findByUsernameOrderByIdDesc(username);
    }

    // Mark notification as read
    @PutMapping("/read/{id}")
    public Notification markAsRead(@PathVariable Long id){

        Notification n = repo.findById(id).orElseThrow();

        n.setReadStatus(true);

        return repo.save(n);
    }

    // Delete notification
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id){

        repo.deleteById(id);

        return "Notification deleted";
    }
}
