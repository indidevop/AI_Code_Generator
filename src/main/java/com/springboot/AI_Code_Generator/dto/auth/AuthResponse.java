package com.springboot.AI_Code_Generator.dto.auth;

// we are returning jwt token and user
public record AuthResponse(
        String token,
        UserProfileResponse user)
{}
