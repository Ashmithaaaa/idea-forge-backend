package com.example.demo.controller;

import com.example.demo.model.CollaborationRequest;
import com.example.demo.service.CollaborationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborations")
@CrossOrigin("*")
public class CollaborationController {

    @Autowired
    private CollaborationService service;

    // SEND REQUEST
    @PostMapping
    public CollaborationRequest sendRequest(@RequestBody CollaborationRequest request) {
        return service.sendRequest(request);
    }

    // GET ALL REQUESTS
    @GetMapping
    public List<CollaborationRequest> getAllRequests() {
        return service.getRequests();
    }

    // GET REQUESTS BY IDEA
    @GetMapping("/idea/{ideaId}")
    public List<CollaborationRequest> getByIdea(@PathVariable Long ideaId) {
        return service.getRequestsByIdea(ideaId);
    }

    // GET USER REQUESTS
    @GetMapping("/user/{username}")
    public List<CollaborationRequest> getUserRequests(@PathVariable String username) {
        return service.getRequestsForUser(username);
    }

    // ACCEPT
    @PutMapping("/{id}/accept")
    public CollaborationRequest accept(@PathVariable Long id) {
        return service.acceptRequest(id);
    }

    // REJECT
    @PutMapping("/{id}/reject")
    public CollaborationRequest reject(@PathVariable Long id) {
        return service.rejectRequest(id);
    }
}