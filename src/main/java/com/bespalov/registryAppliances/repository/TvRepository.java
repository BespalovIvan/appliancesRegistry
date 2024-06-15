package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.Tv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TvRepository extends JpaRepository<Tv, UUID> {
}
