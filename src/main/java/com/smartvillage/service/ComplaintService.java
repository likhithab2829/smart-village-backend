package com.smartvillage.service;

import com.smartvillage.entity.Complaint;
import com.smartvillage.repository.ComplaintRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    // Get all complaints
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // Get complaint by ID
    public Optional<Complaint> getComplaintById(Integer id) {
        return complaintRepository.findById(id);
    }

    // Get complaints by user ID
    public List<Complaint> getComplaintsByUserId(Integer userId) {
        return complaintRepository.findByUserId(userId);
    }

    // Add a new complaint
    public Complaint addComplaint(Complaint complaint) {

        if (complaint.getStatus() == null
                || complaint.getStatus().isBlank()) {
            complaint.setStatus("PENDING");
        }

        if (complaint.getCategory() == null
                || complaint.getCategory().isBlank()) {
            complaint.setCategory("Other");
        }

        if (complaint.getPriority() == null
                || complaint.getPriority().isBlank()) {
            complaint.setPriority("Low");
        }

        if (complaint.getAiExplanation() == null
                || complaint.getAiExplanation().isBlank()) {
            complaint.setAiExplanation(
                    "Complaint was submitted without AI analysis."
            );
        }

        if (complaint.getCreatedAt() == null) {
            complaint.setCreatedAt(LocalDateTime.now());
        }

        return complaintRepository.save(complaint);
    }

    // Update an existing complaint
    public Complaint updateComplaint(Integer id, Complaint complaint) {

        Complaint existingComplaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Complaint not found with ID: " + id
                        )
                );

        // Update status
        if (complaint.getStatus() != null
                && !complaint.getStatus().isBlank()) {
            existingComplaint.setStatus(complaint.getStatus());
        }

        // Update complaint text if provided
        if (complaint.getComplaintText() != null) {
            existingComplaint.setComplaintText(
                    complaint.getComplaintText()
            );
        }

        // Update service ID if provided
        if (complaint.getServiceId() != null) {
            existingComplaint.setServiceId(
                    complaint.getServiceId()
            );
        }

        // Update AI category if provided
        if (complaint.getCategory() != null) {
            existingComplaint.setCategory(
                    complaint.getCategory()
            );
        }

        // Update AI priority if provided
        if (complaint.getPriority() != null) {
            existingComplaint.setPriority(
                    complaint.getPriority()
            );
        }

        // Update AI explanation if provided
        if (complaint.getAiExplanation() != null) {
            existingComplaint.setAiExplanation(
                    complaint.getAiExplanation()
            );
        }

        return complaintRepository.save(existingComplaint);
    }

    // Delete a complaint
    public void deleteComplaint(Integer id) {
        if (!complaintRepository.existsById(id)) {
            throw new RuntimeException(
                    "Complaint not found with ID: " + id
            );
        }

        complaintRepository.deleteById(id);
    }
}