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
@CrossOrigin(origins = "http://localhost:5174") // ✅ FIXED
public class AIController {

    @Value("${groq.api.key}")
    private String apiKey;

    // ✅ GET (for browser testing)
    @GetMapping("/analyze")
    public String analyzeGet(@RequestParam String text) throws Exception {
        return callGroq(text);
    }

    // ✅ POST (for frontend)
    @PostMapping("/analyze")
    public String analyzePost(@RequestBody Map<String, String> body) throws Exception {
    	
        String idea = body.get("idea");
        return callGroq(idea);
    }

    // 🔥 COMMON GROQ LOGIC
    public String callGroq(String idea) throws Exception {

        String prompt =
                "Analyze this startup idea: " + idea +
                ". Give feasibility score, innovation score, market potential, and suggestions.";

        String requestBody = "{"
                + "\"model\":\"llama-3.1-8b-instant\","
                + "\"messages\":["
                + "{ \"role\":\"user\", \"content\":\"" + prompt + "\" }"
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

        String responseBody = response.body();

        // ✅ CLEAN OUTPUT
        int start = responseBody.indexOf("\"content\":\"") + 11;
        int end = responseBody.indexOf("\"", start);

        String cleanText = responseBody.substring(start, end);

        cleanText = cleanText.replace("\\n", "\n")
                             .replace("\\\"", "\"");

        return cleanText;
    }
}