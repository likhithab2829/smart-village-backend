package com.smartvillage.dto;

public class RecommendationResponse {

    private String message;
    private String serviceName;
    private String serviceType;
    private String location;
    private String contactNumber;
    private Boolean available;

    public RecommendationResponse() {
    }

    public RecommendationResponse(
            String message,
            String serviceName,
            String serviceType,
            String location,
            String contactNumber,
            Boolean available) {

        this.message = message;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.location = location;
        this.contactNumber = contactNumber;
        this.available = available;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}