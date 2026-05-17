package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.mapper.ProjectMapper;
import com.springboot.AI_Code_Generator.mapper.ProjectSummaryResponseMapper;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectSummaryResponseMapper projectSummaryResponseMapper;

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
        User user = userRepository.findById(userId).orElseThrow();

        Project p = Project.builder()
                .name(request.name())
                .owner(user)
                .build();

        p = projectRepository.save(p);

        return projectMapper.projectToProjectResponse(p);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        List<Project> projectList = projectRepository.findAllAccessibleByUser(userId);

        return projectList.stream().map(projectSummaryResponseMapper::projectToProjectSummaryResponse).toList();
    }

    @Override
    public ProjectResponse getUserProjectById(Long projectId, Long userId) {

        Project p = getAccessibleProjectById(projectId, userId);

        return projectMapper.projectToProjectResponse(p);
    }

    @Override
    public ProjectResponse updateProject(Long projectId, ProjectRequest request, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);
        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.projectToProjectResponse(project);
    }

    @Override
    public void softDelete(Long projectId, Long userId) {
        Project project=getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("This user is not authorised to delete");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }


// INTERNAL METHODS

    private Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }

}