package com.smartvillage.controller;

import com.smartvillage.entity.VillageService;
import com.smartvillage.service.VillageServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/village-services")
@CrossOrigin(origins = "http://localhost:5173")
public class VillageServiceController {

    private final VillageServiceService service;

    public VillageServiceController(VillageServiceService service) {
        this.service = service;
    }

    @GetMapping
    public List<VillageService> getAllServices() {
        return service.getAllServices();
    }

    @GetMapping("/search")
    public List<VillageService> searchServices(@RequestParam String name) {
        return service.searchServices(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VillageService> getServiceById(@PathVariable Integer id) {
        return service.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VillageService> addService(
            @RequestBody VillageService villageService) {

        return ResponseEntity.ok(service.addService(villageService));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VillageService> updateService(
            @PathVariable Integer id,
            @RequestBody VillageService villageService) {

        return ResponseEntity.ok(
                service.updateService(id, villageService)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Integer id) {
        service.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}