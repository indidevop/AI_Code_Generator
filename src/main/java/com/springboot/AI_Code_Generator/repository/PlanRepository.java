package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,Long> {
    Optional<Plan> findByStripePriceId(String id);
}
