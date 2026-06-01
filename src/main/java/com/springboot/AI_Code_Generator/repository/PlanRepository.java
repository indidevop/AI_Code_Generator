package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan,Long> {
}
