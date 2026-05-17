package com.springboot.AI_Code_Generator.dto.project;

import com.springboot.AI_Code_Generator.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}

