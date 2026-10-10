package com.smartvillage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComplaintAIService {

    private final RestTemplate restTemplate;

    public ComplaintAIService() {
        this.restTemplate = new RestTemplate();
    }

    public String classifyComplaint(String complaintText) {

        if (complaintText == null
                || complaintText.trim().isEmpty()) {

            return "Category: General\n"
                    + "Priority: Low\n"
                    + "Explanation: No complaint text was provided.";
        }

        String prompt =
                "You are an AI complaint classification assistant "
                + "for a Smart Village Service Platform.\n\n"

                + "Classify the complaint into ONE category:\n"
                + "Infrastructure\n"
                + "Healthcare\n"
                + "Education\n"
                + "Transport\n"
                + "Banking\n"
                + "Pharmacy\n"
                + "Government\n"
                + "Emergency\n"
                + "Other\n\n"

                + "Classify priority as ONE of:\n"
                + "Low\n"
                + "Medium\n"
                + "High\n\n"

                + "Return exactly this format:\n"
                + "Category: <category>\n"
                + "Priority: <priority>\n"
                + "Explanation: <short explanation>\n\n"

                + "Complaint:\n"
                + complaintText;

        String ollamaUrl =
                "http://localhost:11434/api/generate";

        Map<String, Object> request =
                new HashMap<>();

        request.put(
                "model",
                "qwen2.5:1.5b"
        );

        request.put(
                "prompt",
                prompt
        );

        request.put(
                "stream",
                false
        );

        try {

            Map<String, Object> response =
                    restTemplate.postForObject(
                            ollamaUrl,
                            request,
                            Map.class
                    );

            if (response != null
                    && response.get("response") != null) {

                return response
                        .get("response")
                        .toString()
                        .trim();
            }

            return "Category: Other\n"
                    + "Priority: Low\n"
                    + "Explanation: Unable to classify complaint.";

        } catch (Exception e) {

            e.printStackTrace();

            return "Category: Other\n"
                    + "Priority: Low\n"
                    + "Explanation: AI service unavailable.";
        }
    }
}