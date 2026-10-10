package com.smartvillage.service;

import com.smartvillage.entity.VillageService;
import com.smartvillage.repository.VillageServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final VillageServiceRepository repository;

    public RecommendationService(
            VillageServiceRepository repository) {

        this.repository = repository;
    }

    public List<VillageService> recommend(String message) {

        if (message == null || message.trim().isEmpty()) {
            return List.of();
        }

        String question =
                message.toLowerCase().trim();

        List<VillageService> services =
                repository.findAll();

        String type = findServiceType(question);

        if (type == null) {
            return List.of();
        }

        return services.stream()
                .filter(service ->
                        service.getServiceType() != null
                        && service.getServiceType()
                        .equalsIgnoreCase(type))
                .toList();
    }


    private String findServiceType(String question) {

        if (containsAny(
                question,
                "medicine",
                "medicines",
                "tablet",
                "tablets",
                "pharmacy",
                "medical store",
                "fever",
                "cold",
                "cough")) {

            return "Pharmacy";
        }


        if (containsAny(
                question,
                "doctor",
                "hospital",
                "clinic",
                "health",
                "treatment",
                "sick")) {

            return "Hospital";
        }


        if (containsAny(
                question,
                "school",
                "schooling",
                "children",
                "child education")) {

            return "School";
        }


        if (containsAny(
                question,
                "college",
                "degree",
                "higher education",
                "university")) {

            return "College";
        }


        if (containsAny(
                question,
                "bank",
                "atm",
                "banking",
                "money")) {

            return "Bank";
        }


        if (containsAny(
                question,
                "grocery",
                "groceries",
                "supermarket",
                "shopping",
                "food")) {

            return "Supermarket";
        }


        if (containsAny(
                question,
                "court",
                "lawyer",
                "legal",
                "law")) {

            return "Court";
        }


        if (containsAny(
                question,
                "government office",
                "government",
                "govt")) {

            return "Government Office";
        }


        if (containsAny(
                question,
                "bus",
                "transport",
                "bus stand",
                "travel")) {

            return "Transport";
        }


        if (containsAny(
                question,
                "ambulance",
                "emergency",
                "accident",
                "urgent")) {

            return "Emergency";
        }


        return null;
    }


    private boolean containsAny(
            String question,
            String... words) {

        for (String word : words) {

            if (question.contains(
                    word.toLowerCase())) {

                return true;
            }
        }

        return false;
    }
}