package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.member.InviteMemberRequest;
import com.springboot.AI_Code_Generator.dto.member.MemberResponse;
import com.springboot.AI_Code_Generator.dto.member.UpdateMemberRoleRequest;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.ProjectMemberId;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.mapper.MemberMapper;
import com.springboot.AI_Code_Generator.repository.ProjectMemberRepository;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.repository.UserRepository;
import com.springboot.AI_Code_Generator.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectRepository projectRepository;
    ProjectMemberRepository projectMemberRepository;
    MemberMapper memberMapper;
    ProjectMemberRepository memberRepository;
    UserRepository userRepository;

    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {

//        Project project = getAccessibleProjectById(projectId, userId);

        return memberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map((projectMember) -> memberMapper.projectMemberToMemberResponse(projectMember))
                        .toList();
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse inviteMember(Long projectId, Long userId, InviteMemberRequest request) {

        // Got the project for projectId and userId
        Project project = getAccessibleProjectById(projectId, userId);

        // Only owner can invite
//        if(!project.getOwner().getId().equals(userId))
//        {
//            throw new RuntimeException("Not allowed");
//        }

        // Find the user to be invited as member using email
        User invitee = userRepository.findByUsername(request.username()).orElseThrow();

        // If email maps to the userId of owner
        if(invitee.getId().equals(userId))
        {
            throw new RuntimeException("Cannot invite yourself!");
        }

        // Create projectMemberId to provide to the projectMember to be created
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,invitee.getId());

        // If projectMember already exists with same id for this project throw exception
        if(projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Member already exists");
        }

        // Create projectMember
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();


        // Save projectMember and return DTO
        return memberMapper.projectMemberToMemberResponse(projectMemberRepository.save(projectMember));

    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long userId, Long memberId, UpdateMemberRoleRequest request) {

        // Got the project for projectId and userId
        Project project = getAccessibleProjectById(projectId, userId);

        // Only owner can update
//        if(!project.getOwner().getId().equals(userId))
//        {
//            throw new RuntimeException("Not allowed");
//        }

        // Create projectMember
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();

        // Set role
        projectMember.setProjectRole(request.role());

        return memberMapper.projectMemberToMemberResponse(projectMemberRepository.save(projectMember));
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public void deleteMemberRole(Long projectId, Long memberId, Long userId) {

        // Got the project for projectId and userId
        Project project = getAccessibleProjectById(projectId, userId);

        // Only owner can delete
//        if(!project.getOwner().getId().equals(userId))
//        {
//            throw new RuntimeException("Not allowed");
//        }

        // Create projectMemberId
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId,memberId);

        if(!projectMemberRepository.existsById(projectMemberId))
        {
            throw new RuntimeException("Member does not exists");
        }

        // Remove from DB
        projectMemberRepository.deleteById(projectMemberId);
    }

    // INTERNAL METHODS

    private Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}
