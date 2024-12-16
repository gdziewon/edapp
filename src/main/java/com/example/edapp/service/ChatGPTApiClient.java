package com.example.edapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGPTApiClient {
    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ChatGPTApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateResponse(String prompt) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("ChatGPT API key is missing. Please configure it in application.properties.");
        }

        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");
        body.put("messages", List.of(Map.of("role", "user", "content", prompt)));
        body.put("temperature", 0.7);
        body.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to fetch response from ChatGPT API: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch response from ChatGPT API: " + e.getMessage(), e);
        }
    }
}



