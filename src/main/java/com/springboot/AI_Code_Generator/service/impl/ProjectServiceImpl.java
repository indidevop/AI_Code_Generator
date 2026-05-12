package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;
import com.springboot.AI_Code_Generator.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        return List.of();
    }

    @Override
    public ProjectResponse getUserProjectById(Long projectId, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
        return null;
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public ProjectResponse deleteProject(Long projectId, Long userId) {
        return null;
    }
}
