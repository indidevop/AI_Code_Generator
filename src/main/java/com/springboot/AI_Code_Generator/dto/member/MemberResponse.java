package com.springboot.AI_Code_Generator.dto.member;

import com.springboot.AI_Code_Generator.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String avatarUrl,
        String name,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
