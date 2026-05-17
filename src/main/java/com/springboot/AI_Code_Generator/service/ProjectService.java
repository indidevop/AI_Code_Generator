package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects(Long userId);

    ProjectResponse getUserProjectById(Long projectId, Long userId);

    ProjectResponse createProject(Long userId, ProjectRequest request);

    ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId);

    void softDelete(Long projectId, Long userId);
}
