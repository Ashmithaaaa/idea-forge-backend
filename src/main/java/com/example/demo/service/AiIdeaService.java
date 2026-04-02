package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiIdeaService {

	public Map<String, String> analyzeIdea(String ideaText){

	    Map<String,String> result = new HashMap<>();

	    ideaText = ideaText.toLowerCase();

	    int score = 5;

	    // keyword scoring
	    if(ideaText.contains("ai") || ideaText.contains("machine learning")){
	        score += 2;
	    }

	    if(ideaText.contains("blockchain") || ideaText.contains("fintech")){
	        score += 2;
	    }

	    if(ideaText.contains("health") || ideaText.contains("medical")){
	        score += 1;
	    }

	    if(ideaText.contains("education") || ideaText.contains("learning")){
	        score += 1;
	    }

	    if(score > 10){
	        score = 10;
	    }

	    String marketPotential = "Medium";

	    if(score >= 8){
	        marketPotential = "High";
	    }
	    else if(score <= 5){
	        marketPotential = "Low";
	    }

	    String suggestion = "Consider validating your target users and competitors.";

	    if(ideaText.contains("health")){
	        suggestion = "Check healthcare regulations and data privacy compliance.";
	    }

	    if(ideaText.contains("fintech")){
	        suggestion = "Ensure financial security and regulatory compliance.";
	    }

	    result.put("InnovationScore", score + "/10");
	    result.put("MarketPotential", marketPotential);
	    result.put("Suggestion", suggestion);

	    return result;
	}
}