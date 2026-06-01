package com.springboot.AI_Code_Generator.security;

import com.springboot.AI_Code_Generator.enums.ProjectRole;
import com.springboot.AI_Code_Generator.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpressions {

    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    // a ProjectMember row must exist for that userId and projectId then we can say user is a member
    public boolean canViewProject(Long projectId){
            Long currentUserId=authUtil.getCurrentUserId();
            return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,currentUserId)
                    .map(role -> role.equals(ProjectRole.OWNER) || role.equals(ProjectRole.EDITOR) || role.equals(ProjectRole.VIEWER))
                .orElse(false);
    }

    public boolean canEditProject(Long projectId){
        Long currentUserId=authUtil.getCurrentUserId();
        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,currentUserId)
                .map(role -> role.equals(ProjectRole.OWNER) || role.equals(ProjectRole.EDITOR))
                .orElse(false);
    }
}
