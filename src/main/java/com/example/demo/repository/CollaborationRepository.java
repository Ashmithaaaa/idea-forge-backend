package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CollaborationRequest;

public interface CollaborationRepository
        extends JpaRepository<CollaborationRequest, Long> {

    List<CollaborationRequest> findByIdeaId(Long ideaId);

    List<CollaborationRequest> findByRequesterName(String requesterName);

    Optional<CollaborationRequest> findByIdeaIdAndRequesterName(
            Long ideaId,
            String requesterName
    );
}