package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    // Prevent startup failure if key is missing
    @Value("${groq.api.key:dummy}")
    private String apiKey;

    @GetMapping("/analyze")
    public String analyzeGet(@RequestParam String text) throws Exception {
        return callGroq(text);
    }

    @PostMapping("/analyze")
    public String analyzePost(@RequestBody Map<String, String> body) throws Exception {

        String idea = body.get("idea");

        if (idea == null || idea.trim().isEmpty()) {
            return "Idea cannot be empty";
        }

        return callGroq(idea);
    }

    private String callGroq(String idea) throws Exception {

        if ("dummy".equals(apiKey)) {
            return "Groq API key is not configured.";
        }

        String prompt =
                "Analyze this startup idea: " + idea +
                ". Give feasibility score, innovation score, market potential, and suggestions.";

        String requestBody = "{"
                + "\"model\":\"llama-3.1-8b-instant\","
                + "\"messages\":["
                + "{ \"role\":\"user\", \"content\":\"" + prompt.replace("\"", "\\\"") + "\" }"
                + "]"
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.groq.com/openai/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}