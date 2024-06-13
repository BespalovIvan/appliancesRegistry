package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.ModelAppliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelApplianceRepository extends JpaRepository<ModelAppliance, UUID> {
}
