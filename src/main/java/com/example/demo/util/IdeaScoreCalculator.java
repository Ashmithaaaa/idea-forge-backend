package com.example.demo.util;

import java.time.Duration;
import java.time.LocalDateTime;

import com.example.demo.model.Idea;

public class IdeaScoreCalculator {

    public static double calculateScore(Idea idea) {

        int votes = idea.getVotes();
        int views = idea.getViews();

        double voteWeight = 3.0;
        double viewWeight = 0.5;

        // time decay (older ideas lose score)
        long hours = Duration.between(idea.getCreatedAt(), LocalDateTime.now()).toHours();

        double timePenalty = hours * 0.05;

        double score = (votes * voteWeight) + (views * viewWeight) - timePenalty;

        return score;
    }
}