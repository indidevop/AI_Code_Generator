package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.member.InviteMemberRequest;
import com.springboot.AI_Code_Generator.dto.member.MemberResponse;
import com.springboot.AI_Code_Generator.dto.member.UpdateMemberRoleRequest;
import com.springboot.AI_Code_Generator.entity.ProjectMember;
import com.springboot.AI_Code_Generator.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getProjectMembers(@PathVariable Long projectId)
    {
        Long userId = 1L;
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId,userId));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(@PathVariable Long projectId, @RequestBody InviteMemberRequest request){
        Long userId=1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectMemberService.inviteMember(projectId, userId, request));

    }

    @PatchMapping("{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(@PathVariable Long projectId, @RequestBody UpdateMemberRoleRequest request, @PathVariable Long memberId){
        Long userId=2L;
        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, userId, request));
    }

    @DeleteMapping("{memberId}")
    public ResponseEntity<MemberResponse> deleteMemberRole(@PathVariable Long projectId, @PathVariable Long memberId){
        Long userId=2L;
        return ResponseEntity.ok(projectMemberService.deleteMemberRole(projectId, memberId, userId));
    }
}
