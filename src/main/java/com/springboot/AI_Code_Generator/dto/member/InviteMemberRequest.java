package com.springboot.AI_Code_Generator.dto.member;

import com.springboot.AI_Code_Generator.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
