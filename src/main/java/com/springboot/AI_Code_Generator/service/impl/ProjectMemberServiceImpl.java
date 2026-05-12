package com.springboot.AI_Code_Generator.service.impl;

import com.springboot.AI_Code_Generator.dto.member.InviteMemberRequest;
import com.springboot.AI_Code_Generator.dto.member.MemberResponse;
import com.springboot.AI_Code_Generator.dto.member.UpdateMemberRoleRequest;
import com.springboot.AI_Code_Generator.service.ProjectMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        return List.of();
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
}
