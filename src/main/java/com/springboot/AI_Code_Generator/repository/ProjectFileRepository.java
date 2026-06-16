package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.dto.project.FileNode;
import com.springboot.AI_Code_Generator.entity.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectFileRepository extends JpaRepository<ProjectFile,Long> {
    Optional<ProjectFile> findByProjectIdAndPath(Long projectId, String cleanPath);

    List<ProjectFile> findByProjectId(Long projectId);
}
