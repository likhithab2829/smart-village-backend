package com.smartvillage.controller;

import com.smartvillage.entity.VillageService;
import com.smartvillage.service.RecommendationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "http://localhost:5173")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {

        this.recommendationService =
                recommendationService;
    }


    @GetMapping
    public ResponseEntity<List<VillageService>> recommend(
            @RequestParam String message) {

        List<VillageService> results =
                recommendationService
                        .recommend(message);

        return ResponseEntity.ok(results);
    }
}