package com.springboot.AI_Code_Generator.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String avatarUrl,
        String name
) {}
