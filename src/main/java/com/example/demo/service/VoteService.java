package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Idea;
import com.example.demo.model.Vote;
import com.example.demo.repository.IdeaRepository;
import com.example.demo.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private IdeaRepository ideaRepo;

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private ActivityService activityService;

    // =========================
    // UPVOTE IDEA
    // =========================
    public Idea upvoteIdea(Long ideaId, String username){

        Idea idea = ideaRepo.findById(ideaId).orElse(null);

        if(idea == null){
            return null;
        }

        Optional<Vote> existingVote =
                voteRepo.findByIdeaIdAndUsername(ideaId, username);

        if(existingVote.isPresent()){
            return idea;
        }

        Vote vote = new Vote();
        vote.setIdeaId(ideaId);
        vote.setUsername(username);

        voteRepo.save(vote);

        activityService.logActivity(
                username,
                "upvoted",
                "Idea " + ideaId
        );

        idea.setVotes(idea.getVotes() + 1);

        ideaRepo.save(idea);

        return idea;
    }

    // =========================
    // DOWNVOTE IDEA
    // =========================
    public Idea downvoteIdea(Long ideaId, String username){

        Idea idea = ideaRepo.findById(ideaId).orElse(null);

        if(idea == null){
            return null;
        }

        Optional<Vote> existingVote =
                voteRepo.findByIdeaIdAndUsername(ideaId, username);

        if(existingVote.isPresent()){

            voteRepo.delete(existingVote.get());

            idea.setVotes(Math.max(0, idea.getVotes() - 1));

            ideaRepo.save(idea);

            activityService.logActivity(
                    username,
                    "removed vote from",
                    "Idea " + ideaId
            );
        }

        return idea;
    }
}