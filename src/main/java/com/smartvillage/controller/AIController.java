package com.smartvillage.controller;

import com.smartvillage.dto.AIChatRequest;
import com.smartvillage.dto.AIChatResponse;
import com.smartvillage.service.AIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {

        this.aiService = aiService;
    }


    @PostMapping("/chat")
    public ResponseEntity<AIChatResponse> chat(
            @RequestBody AIChatRequest request) {

        String response =
                aiService.getResponse(
                        request.getMessage()
                );

        return ResponseEntity.ok(
                new AIChatResponse(response)
        );
    }
}