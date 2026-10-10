package com.smartvillage.service;

import com.smartvillage.entity.VillageService;
import com.smartvillage.repository.VillageServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final RestTemplate restTemplate;
    private final VillageServiceRepository villageServiceRepository;

    public AIService(VillageServiceRepository villageServiceRepository) {
        this.restTemplate = new RestTemplate();
        this.villageServiceRepository = villageServiceRepository;
    }

    public String getResponse(String message) {

        if (message == null || message.trim().isEmpty()) {
            return "Please enter a question.";
        }

        String question = message.toLowerCase().trim();

        // Get services from MySQL
        List<VillageService> services =
                villageServiceRepository.findAll();

        if (services.isEmpty()) {
            return "Sorry, no village services are available.";
        }

        /*
         * --------------------------------------------------
         * STEP 1: Understand common words used by users
         * --------------------------------------------------
         */

        String requestedType = null;

        // Hospital
        if (containsAny(question,
                "hospital",
                "doctor",
                "medical",
                "health",
                "clinic",
                "treatment",
                "sick",
                "medicine for illness")) {

            requestedType = "Hospital";
        }

        // Pharmacy
        else if (containsAny(question,
                "pharmacy",
                "medicine",
                "medicines",
                "tablet",
                "tablets",
                "drug",
                "medical store",
                "buy medicine",
                "buy medicines")) {

            requestedType = "Pharmacy";
        }

        // School
        else if (containsAny(question,
                "school",
                "schooling",
                "children",
                "child education",
                "primary education",
                "student")) {

            requestedType = "School";
        }

        // College
        else if (containsAny(question,
                "college",
                "degree",
                "higher education",
                "university")) {

            requestedType = "College";
        }

        // Bank
        else if (containsAny(question,
                "bank",
                "banking",
                "atm",
                "money",
                "account",
                "financial")) {

            requestedType = "Bank";
        }

        // Supermarket
        else if (containsAny(question,
                "supermarket",
                "grocery",
                "groceries",
                "shopping",
                "shop",
                "store",
                "food")) {

            requestedType = "Supermarket";
        }

        // Court
        else if (containsAny(question,
                "court",
                "legal",
                "law",
                "lawyer",
                "legal help")) {

            requestedType = "Court";
        }

        // Government office
        else if (containsAny(question,
                "government",
                "government office",
                "govt",
                "official office",
                "government service")) {

            requestedType = "Government Office";
        }

        // Transport
        else if (containsAny(question,
                "bus",
                "transport",
                "travel",
                "bus stand",
                "public transport")) {

            requestedType = "Transport";
        }

        // Emergency
        else if (containsAny(question,
                "emergency",
                "ambulance",
                "accident",
                "urgent",
                "immediate help")) {

            requestedType = "Emergency";
        }

        /*
         * --------------------------------------------------
         * STEP 2: Find matching services from MySQL
         * --------------------------------------------------
         */

        List<VillageService> matchedServices = services;

        if (requestedType != null) {

            final String type = requestedType;

            matchedServices = services.stream()
                    .filter(service ->
                            service.getServiceType() != null
                                    && service.getServiceType()
                                    .equalsIgnoreCase(type))
                    .toList();
        }

        /*
         * --------------------------------------------------
         * STEP 3: If service was found, show actual data
         * --------------------------------------------------
         */

        if (requestedType != null && !matchedServices.isEmpty()) {

            return buildServiceResponse(
                    matchedServices,
                    requestedType
            );
        }

        /*
         * --------------------------------------------------
         * STEP 4: If service type was understood but
         * service doesn't exist in database
         * --------------------------------------------------
         */

        if (requestedType != null) {

            return "Sorry, there is currently no "
                    + requestedType
                    + " service available in the village database.";
        }

        /*
         * --------------------------------------------------
         * STEP 5: General question → send database context
         * to Qwen
         * --------------------------------------------------
         */

        StringBuilder databaseInformation =
                new StringBuilder();

        databaseInformation.append(
                "ACTUAL VILLAGE SERVICES:\n\n"
        );

        for (VillageService service : services) {

            databaseInformation
                    .append("Service: ")
                    .append(safe(service.getServiceName()))
                    .append("\n")

                    .append("Type: ")
                    .append(safe(service.getServiceType()))
                    .append("\n")

                    .append("Location: ")
                    .append(safe(service.getLocation()))
                    .append("\n")

                    .append("Contact: ")
                    .append(safe(service.getContactNumber()))
                    .append("\n")

                    .append("Available: ")
                    .append(
                            Boolean.TRUE.equals(
                                    service.getAvailable()
                            )
                                    ? "Yes"
                                    : "No"
                    )
                    .append("\n\n");
        }

        String prompt =
                "You are the Smart Village AI Assistant.\n\n"

                + "Answer the user's question using ONLY "
                + "the actual village service information "
                + "provided below.\n\n"

                + "Do not invent locations, services or "
                + "phone numbers.\n\n"

                + databaseInformation

                + "\nUSER QUESTION:\n"
                + message

                + "\n\nANSWER:";

        return askOllama(prompt);
    }

    /*
     * --------------------------------------------------
     * Check whether question contains any keyword
     * --------------------------------------------------
     */

    private boolean containsAny(
            String question,
            String... words) {

        for (String word : words) {

            if (question.contains(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    /*
     * --------------------------------------------------
     * Create response from actual database information
     * --------------------------------------------------
     */

    private String buildServiceResponse(
            List<VillageService> services,
            String type) {

        StringBuilder response =
                new StringBuilder();

        response.append(
                "I found the following "
        ).append(type)
                .append(" service(s) in the village:\n\n");

        for (VillageService service : services) {

            response.append("🏠 ")
                    .append(
                            safe(service.getServiceName())
                    )
                    .append("\n");

            response.append("📍 Location: ")
                    .append(
                            safe(service.getLocation())
                    )
                    .append("\n");

            response.append("📞 Contact: ")
                    .append(
                            safe(service.getContactNumber())
                    )
                    .append("\n");

            response.append("✅ Available: ")
                    .append(
                            Boolean.TRUE.equals(
                                    service.getAvailable()
                            )
                                    ? "Yes"
                                    : "No"
                    )
                    .append("\n\n");
        }

        return response.toString().trim();
    }

    /*
     * --------------------------------------------------
     * Send general questions to Qwen
     * --------------------------------------------------
     */

    private String askOllama(String prompt) {

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

            return "Sorry, I could not get a response from the AI.";

        } catch (Exception e) {

            e.printStackTrace();

            return "AI service is currently unavailable. "
                    + "Please make sure Ollama is running.";
        }
    }

    /*
     * --------------------------------------------------
     * Handle null database values
     * --------------------------------------------------
     */

    private String safe(String value) {

        if (value == null || value.trim().isEmpty()) {
            return "Not available";
        }

        return value;
    }
}