package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SmartphoneRepository extends JpaRepository<Smartphone, UUID> {
}
