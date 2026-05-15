package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.mapper.ProjectMapper;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(Long userId, ProjectRequest request) {
         User user=userRepository.findById(userId).orElseThrow();

         Project p= Project.builder()
                 .name(request.name())
                 .owner(user)
                 .build();

         p=projectRepository.save(p);

         return projectMapper.projectToProjectResponse(p);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        return List.of();
    }

    @Override
    public ProjectResponse getUserProjectById(Long projectId, Long userId) {
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
