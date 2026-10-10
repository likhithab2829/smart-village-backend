package com.smartvillage.dto;

public class ComplaintAIResponse {

    private String category;
    private String priority;
    private String explanation;

    public ComplaintAIResponse() {
    }

    public ComplaintAIResponse(
            String category,
            String priority,
            String explanation) {

        this.category = category;
        this.priority = priority;
        this.explanation = explanation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}