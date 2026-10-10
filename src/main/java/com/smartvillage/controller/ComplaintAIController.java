package com.smartvillage.controller;

import com.smartvillage.dto.ComplaintAIRequest;
import com.smartvillage.dto.ComplaintAIResponse;
import com.smartvillage.service.ComplaintAIService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaint-ai")
@CrossOrigin(origins = "http://localhost:5173")
public class ComplaintAIController {

    private final ComplaintAIService complaintAIService;

    public ComplaintAIController(
            ComplaintAIService complaintAIService) {

        this.complaintAIService =
                complaintAIService;
    }

    @PostMapping("/classify")
    public ResponseEntity<ComplaintAIResponse> classify(
            @RequestBody ComplaintAIRequest request) {

        String result =
                complaintAIService.classifyComplaint(
                        request.getComplaintText()
                );

        String category = "Other";
        String priority = "Low";
        String explanation = result;

        String[] lines = result.split("\\r?\\n");

        for (String line : lines) {

            if (line.startsWith("Category:")) {

                category =
                        line.substring("Category:".length())
                                .trim();
            }

            else if (line.startsWith("Priority:")) {

                priority =
                        line.substring("Priority:".length())
                                .trim();
            }

            else if (line.startsWith("Explanation:")) {

                explanation =
                        line.substring(
                                "Explanation:".length()
                        ).trim();
            }
        }

        ComplaintAIResponse response =
                new ComplaintAIResponse(
                        category,
                        priority,
                        explanation
                );

        return ResponseEntity.ok(response);
    }
}