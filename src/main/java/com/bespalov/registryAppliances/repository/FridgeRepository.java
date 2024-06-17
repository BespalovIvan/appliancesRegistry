package com.bespalov.registryAppliances.repository;

import com.bespalov.registryAppliances.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, UUID>, QuerydslPredicateExecutor<Fridge> {
}
