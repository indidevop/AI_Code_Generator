package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.project.ProjectRequest;
import com.springboot.AI_Code_Generator.dto.project.ProjectResponse;
import com.springboot.AI_Code_Generator.dto.project.ProjectSummaryResponse;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.ProjectMemberId;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.enums.ProjectRole;
import com.springboot.AI_Code_Generator.error.ResourceNotFoundException;
import com.springboot.AI_Code_Generator.mapper.ProjectMapper;
import com.springboot.AI_Code_Generator.mapper.ProjectSummaryResponseMapper;
import com.springboot.AI_Code_Generator.repository.ProjectMemberRepository;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.ProjectService;
import com.springboot.AI_Code_Generator.service.ProjectTemplateService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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
    ProjectMemberRepository projectMemberRepository;
    AuthUtil authUtil;
    ProjectTemplateService projectTemplateService;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        Long userId = authUtil.getCurrentUserId();

        // User owner = userRepository.findById(userId).orElseThrow();

        // This query will not make DB call
        // owner will only have userId set, other field are not required
        // Works only in Transactional context
        User owner = userRepository.getReferenceById(userId);

        Project p = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();
        p = projectRepository.save(p);

        ProjectMemberId projectmemberId = new ProjectMemberId(p.getId(),owner.getId());

        ProjectMember projectMember = ProjectMember.builder()
                .id(projectmemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(p)
                .build();

        projectMemberRepository.save(projectMember);

        projectTemplateService.initializeProjectFromTemplate(p.getId());

        return projectMapper.projectToProjectResponse(p);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {

        Long userId = authUtil.getCurrentUserId();

        List<Project> projectList = projectRepository.findAllAccessibleByUser(userId);

        return projectList.stream().map(projectSummaryResponseMapper::projectToProjectSummaryResponse).toList();
    }

    @Override
    // This method checks if user is member of that project
    // Before coming to service layer user authorization must be checked
    @PreAuthorize("@security.canViewProject(#projectId)") // security is the custom bean name we created for SecurityExpressions, passing projectId to canViewProject method
    public ProjectResponse getUserProjectById(Long projectId) {

        Long userId = authUtil.getCurrentUserId();

        Project p = getAccessibleProjectById(projectId, userId);

        return projectMapper.projectToProjectResponse(p);
    }

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {

        Long userId = authUtil.getCurrentUserId();

        Project project = getAccessibleProjectById(projectId, userId);

//        if(!project.getOwner().getId().equals(userId)){
//            throw new RuntimeException("This user is not authorised to update");
//        }

        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.projectToProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#projectId)")
    public void softDelete(Long projectId) {

        Long userId = authUtil.getCurrentUserId();

        Project project=getAccessibleProjectById(projectId, userId);

//        if(!project.getOwner().getId().equals(userId)){
//            throw new RuntimeException("This user is not authorised to delete");
//        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }


// INTERNAL METHODS

    private Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId)
                .orElseThrow(()-> new ResourceNotFoundException("", projectId.toString()));
    }

}