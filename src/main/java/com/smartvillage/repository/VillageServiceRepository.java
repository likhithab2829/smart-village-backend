package com.smartvillage.repository;

import com.smartvillage.entity.VillageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VillageServiceRepository extends JpaRepository<VillageService, Integer> {

    List<VillageService> findByServiceNameContainingIgnoreCase(String serviceName);

}