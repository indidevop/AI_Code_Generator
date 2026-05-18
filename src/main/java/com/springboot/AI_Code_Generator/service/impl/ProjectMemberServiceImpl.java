package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.member.InviteMemberRequest;
import com.springboot.AI_Code_Generator.dto.member.MemberResponse;
import com.springboot.AI_Code_Generator.dto.member.UpdateMemberRoleRequest;
import com.springboot.AI_Code_Generator.entity.Project;
import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.entity.User;
import com.springboot.AI_Code_Generator.mapper.MemberMapper;
import com.springboot.AI_Code_Generator.repository.ProjectMemberRepository;
import com.springboot.AI_Code_Generator.repository.ProjectRepository;
import com.springboot.AI_Code_Generator.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectRepository projectRepository;
    MemberMapper memberMapper;
    ProjectMemberRepository memberRepository;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {

        Project project = getAccessibleProjectById(projectId, userId);
        User owner = project.getOwner();

        List<MemberResponse> returnList = new ArrayList<>();
        returnList.add(memberMapper.userToMemberResponseFromOwner(owner));

        returnList.addAll(
                memberRepository.findByIdProjectId(projectId)
                        .stream()
                        .map((projectMember) -> memberMapper.projectMemberToMemberResponse(projectMember))
                        .toList());

        return returnList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, Long userId, InviteMemberRequest request) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long userId, Long memberId, UpdateMemberRoleRequest request) {
        return null;
    }

    @Override
    public MemberResponse deleteMemberRole(Long projectId, Long memberId, Long userId) {
        return null;
    }

    // INTERNAL METHODS

    private Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}
