package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.ModelAppliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelApplianceRepository extends JpaRepository<ModelAppliance, UUID> {

}
