package com.smartvillage.controller;

import com.smartvillage.entity.Complaint;
import com.smartvillage.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:5173")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // ==========================================
    // GET ALL COMPLAINTS
    // ==========================================

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }


    // ==========================================
    // GET COMPLAINT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(
            @PathVariable Integer id) {

        return complaintService.getComplaintById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                    () -> ResponseEntity.notFound().build()
                );
    }


    // ==========================================
    // GET COMPLAINTS BY USER ID
    // ==========================================

    @GetMapping("/user/{userId}")
    public List<Complaint> getComplaintsByUser(
            @PathVariable Integer userId) {

        return complaintService
                .getComplaintsByUserId(userId);
    }


    // ==========================================
    // ADD COMPLAINT
    // ==========================================

    @PostMapping
    public ResponseEntity<Complaint> addComplaint(
            @RequestBody Complaint complaint) {

        Complaint savedComplaint =
                complaintService.addComplaint(complaint);

        return ResponseEntity.ok(savedComplaint);
    }


    // ==========================================
    // UPDATE COMPLAINT
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaint(
            @PathVariable Integer id,
            @RequestBody Complaint complaint) {

        Complaint updatedComplaint =
                complaintService.updateComplaint(
                        id,
                        complaint
                );

        return ResponseEntity.ok(updatedComplaint);
    }


    // ==========================================
    // DELETE COMPLAINT
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(
            @PathVariable Integer id) {

        complaintService.deleteComplaint(id);

        return ResponseEntity.noContent().build();
    }
}