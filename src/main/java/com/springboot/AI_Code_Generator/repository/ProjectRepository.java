package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
