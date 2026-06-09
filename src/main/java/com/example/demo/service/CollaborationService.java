package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CollaborationRequest;
import com.example.demo.model.Idea;
import com.example.demo.repository.CollaborationRepository;
import com.example.demo.repository.IdeaRepository;

@Service
public class CollaborationService {

    @Autowired
    private CollaborationRepository collaborationRepo;

    @Autowired
    private IdeaRepository ideaRepo;


    // ==============================
    // SEND COLLABORATION REQUEST
    // ==============================

    public CollaborationRequest sendRequest(CollaborationRequest request) {

        Optional<CollaborationRequest> existing =
                collaborationRepo.findByIdeaIdAndRequesterName(
                        request.getIdeaId(),
                        request.getRequesterName()
                );

        if (existing.isPresent() &&
            existing.get().getStatus().equalsIgnoreCase("PENDING")) {

            throw new RuntimeException("Request already pending");
        }

        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());

        return collaborationRepo.save(request);
    }


    // ==============================
    // GET ALL REQUESTS
    // ==============================

    public List<CollaborationRequest> getRequests() {

        List<CollaborationRequest> list = collaborationRepo.findAll();

        if(list == null){
            return new ArrayList<>();
        }

        return list;
    }


    // ==============================
    // GET REQUESTS BY IDEA
    // ==============================

    public List<CollaborationRequest> getRequestsByIdea(Long ideaId) {

        List<CollaborationRequest> list =
                collaborationRepo.findByIdeaId(ideaId);

        if(list == null){
            return new ArrayList<>();
        }

        return list;
    }


    // ==============================
    // GET REQUESTS FOR USER
    // ==============================

    public List<CollaborationRequest> getRequestsForUser(String username) {

        List<CollaborationRequest> list =
                collaborationRepo.findByRequesterName(username);

        if(list == null){
            return new ArrayList<>();
        }

        return list;
    }


    // ==============================
    // ACCEPT REQUEST
    // ==============================

    public CollaborationRequest acceptRequest(Long id) {

        CollaborationRequest request =
                collaborationRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Request not found"));

        request.setStatus("ACCEPTED");

        collaborationRepo.save(request);

        Idea idea = ideaRepo.findById(request.getIdeaId()).orElse(null);

        if (idea != null) {

            List<String> contributors = idea.getContributors();

            if(contributors == null){
                contributors = new ArrayList<>();
            }

            if (!contributors.contains(request.getRequesterName())) {
                contributors.add(request.getRequesterName());
            }

            idea.setContributors(contributors);

            ideaRepo.save(idea);
        }

        return request;
    }


    // ==============================
    // REJECT REQUEST
    // ==============================

    public CollaborationRequest rejectRequest(Long id) {

        CollaborationRequest request =
                collaborationRepo.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Request not found"));

        request.setStatus("REJECTED");

        return collaborationRepo.save(request);
    }

}