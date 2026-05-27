package com.springboot.AI_Code_Generator.service;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects();

    ProjectResponse getUserProjectById(Long projectId);

    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long projectId, ProjectRequest request);

    void softDelete(Long projectId);
}
