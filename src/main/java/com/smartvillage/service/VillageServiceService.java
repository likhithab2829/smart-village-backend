package com.smartvillage.service;

import com.smartvillage.entity.VillageService;
import com.smartvillage.repository.VillageServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VillageServiceService {

    private final VillageServiceRepository repository;

    public VillageServiceService(VillageServiceRepository repository) {
        this.repository = repository;
    }

    // Get all services
    public List<VillageService> getAllServices() {
        return repository.findAll();
    }

    // Get service by ID
    public Optional<VillageService> getServiceById(Integer id) {
        return repository.findById(id);
    }

    // Add service
    public VillageService addService(VillageService villageService) {
        return repository.save(villageService);
    }

    // Update service
    public VillageService updateService(Integer id, VillageService updatedService) {

        VillageService existingService = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existingService.setServiceName(updatedService.getServiceName());
        existingService.setServiceType(updatedService.getServiceType());
        existingService.setLocation(updatedService.getLocation());
        existingService.setContactNumber(updatedService.getContactNumber());
        existingService.setAvailable(updatedService.getAvailable());

        return repository.save(existingService);
    }

    // Delete service
    public void deleteService(Integer id) {
        repository.deleteById(id);
    }
    public List<VillageService> searchServices(String name) {
    return repository.findByServiceNameContainingIgnoreCase(name);
    }
}