package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplianceRepository extends JpaRepository<Appliance, UUID> {
}
