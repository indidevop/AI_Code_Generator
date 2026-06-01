package com.springboot.AI_Code_Generator.security;

import com.springboot.AI_Code_Generator.enums.ProjectPermission;
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
            return hasPermission(projectId,ProjectPermission.VIEW);
    }

    public boolean canEditProject(Long projectId){
        return hasPermission(projectId,ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId,ProjectPermission.DELETE);
    }

    public boolean canViewMembers(Long projectId){
        return hasPermission(projectId,ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(Long projectId){
        return hasPermission(projectId,ProjectPermission.MANAGE_MEMBERS);
    }

    private boolean hasPermission(Long projectId, ProjectPermission projectPermission){
        {
            Long currentUserId=authUtil.getCurrentUserId();
            return projectMemberRepository.findRoleByProjectIdAndUserId(projectId,currentUserId)
                    .map(role -> role.getPermissions().contains(projectPermission))
                    .orElse(false);
        }

    }
}
